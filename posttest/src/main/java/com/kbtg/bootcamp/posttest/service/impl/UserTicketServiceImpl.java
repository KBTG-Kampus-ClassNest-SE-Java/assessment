package com.kbtg.bootcamp.posttest.service.impl;

import com.kbtg.bootcamp.posttest.dto.response.PurchaseTicketResponseDTO;
import com.kbtg.bootcamp.posttest.entity.Ticket;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.ElementNotFoundException;
import com.kbtg.bootcamp.posttest.exception.IllegalOperationException;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
