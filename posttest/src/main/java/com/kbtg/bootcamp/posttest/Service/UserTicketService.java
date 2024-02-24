package com.kbtg.bootcamp.posttest.Service;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
import com.kbtg.bootcamp.posttest.Exception.ConflictException;
import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.UserTicket.UserTicketResponseDto;
import org.springframework.stereotype.Service;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserTicketService {
    private final com.kbtg.bootcamp.posttest.UserTicket.UserTicketRepository UserTicketRepository;
    private final LotteryRepository lotteryRepository;

    public UserTicketService(UserTicketRepository user_ticketRepository, LotteryRepository lotteryRepository) {
        this.UserTicketRepository = user_ticketRepository;
        this.lotteryRepository = lotteryRepository;
    }

    public String BuyTicket(String userId, String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicket(ticketId);

        if (lottery.isEmpty() || lottery.get().getAmount() == 0)
            throw new NotFoundException("Lottery with ticketId " + ticketId + " not found or ticket is sold out");

        if (UserTicketRepository.findByUserIdAndTicket(userId, ticketId).isPresent())
            throw new ConflictException("User already has a ticket " + ticketId);

        lottery.get().setAmount(lottery.get().getAmount() - 1);
        lotteryRepository.save(lottery.get());
        UserTicket userticket = new UserTicket(userId,"buy", 1L, lottery.get());

        return UserTicketRepository.save(userticket).getId().toString();
    }

    public UserTicketResponseDto getUserTicket(String userId) {
        List<Lottery> lotteries = lotteryRepository.findByUserTicketUserId(userId);
        List<String> tickets = lotteries.stream().map(Lottery::getTicket).toList();

        if (tickets.isEmpty())
            throw new NotFoundException("No tickets found for user with userId: " + userId);

        Long count = tickets.stream().mapToLong(ticket -> 1).sum();
        Double cost = lotteries.stream().mapToDouble(Lottery::getPrice).sum();
        return new UserTicketResponseDto(tickets, count, cost);
    }

    public String deleteLottery(String userId, String ticketId) {
        Optional<UserTicket> user_tickets = UserTicketRepository.findByUserIdAndTicket(userId, ticketId);

        if (user_tickets.isEmpty())
            throw new NotFoundException("User with userId " + userId + " not found");

        UserTicketRepository.delete(user_tickets.get());
        return ticketId;
    }
}
