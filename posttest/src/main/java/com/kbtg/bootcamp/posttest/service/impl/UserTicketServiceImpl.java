package com.kbtg.bootcamp.posttest.service.impl;

import com.kbtg.bootcamp.posttest.dto.response.PurchaseTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.RefundTicketResponseDTO;
import com.kbtg.bootcamp.posttest.dto.response.UserPurchaseHistoryResponseDTO;
import com.kbtg.bootcamp.posttest.entity.Ticket;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.ElementNotFoundException;
import com.kbtg.bootcamp.posttest.exception.IllegalOperationException;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    private final UserTicketRepository userTicketRepository;
    private final TicketRepository ticketRepository;

    public UserTicketServiceImpl(UserTicketRepository userTicketRepository, TicketRepository ticketRepository) {
        this.userTicketRepository = userTicketRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public PurchaseTicketResponseDTO purchaseTicket(String userId, String ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);

        // Not exists ticket and out of stock ticket can't be purchased
        if(ticket.isEmpty()) throw new ElementNotFoundException("Ticket Doesn't exists with id : " + ticketId);
        if(ticket.get().getAmount() == 0) throw new IllegalOperationException("Ticket out of stock");

        // decrease amount of lottery when user purchases
        // 1 is number of lottery that have been purchase
        Ticket purchaseTicket = ticket.get();
        purchaseTicket.setAmount(purchaseTicket.getAmount() - 1);
        purchaseTicket = ticketRepository.save(purchaseTicket);

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicket(purchaseTicket);
        UserTicket createdUserTicket = userTicketRepository.save(userTicket);

        return new PurchaseTicketResponseDTO(createdUserTicket.getId());
    }

    @Override
    public UserPurchaseHistoryResponseDTO showHistoryPurchase(String userId) {
        List<Ticket> historyTickets = userTicketRepository.findByUserId(userId)
                .stream()
                .map(UserTicket::getTicket).toList();

        return new UserPurchaseHistoryResponseDTO(
                historyTickets.stream().map(Ticket::getTicket_no).collect(Collectors.toSet()),
                historyTickets.size(),
                historyTickets.stream().mapToInt(Ticket::getPrice).sum()
        );
    }

    @Override
    public RefundTicketResponseDTO refundTicket(String userId, String ticketId) {

        List<UserTicket> refundTicket = userTicketRepository.findByUserId(userId)
                .stream()
                .filter(userTicket -> userTicket.getTicket().getTicket_no().equals(ticketId))
                .toList();


        // Return with bad request because user id and ticket id might be existing.
        // However, user didn't purchase the lottery.
        // Hence, user trying to refund lottery which is not afforded yet(Illegal operation)
        if(refundTicket.isEmpty()) {
            throw new IllegalOperationException("User ID : " + userId + " didn't purchase lottery number : " + ticketId);
        }

        // ignore to handle optional because ticketId always exists
        Ticket ticket = ticketRepository.findById(ticketId).get();

        ticket.setAmount(ticket.getAmount() + refundTicket.size());
        userTicketRepository.deleteAll(refundTicket);

        return new RefundTicketResponseDTO(ticketId);
    }


}
