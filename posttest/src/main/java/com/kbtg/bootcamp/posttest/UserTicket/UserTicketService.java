package com.kbtg.bootcamp.posttest.UserTicket;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
import com.kbtg.bootcamp.posttest.Exception.ConflictException;
import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserTicketService {
    private final UserTicketRepository user_ticketRepository;
    private final LotteryRepository lotteryRepository;

    public UserTicketService(UserTicketRepository user_ticketRepository, LotteryRepository lotteryRepository) {
        this.user_ticketRepository = user_ticketRepository;
        this.lotteryRepository = lotteryRepository;
    }

    public String BuyTicket(String userId, String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicket(ticketId);

        if (!lottery.isPresent())
            throw new NotFoundException("Lottery with ticketId " + ticketId + " not found");

        if (user_ticketRepository.findByUserIdAndTicket(userId, ticketId).isPresent())
            throw new ConflictException("User already has a ticket " + ticketId);

        UserTicket user_ticket = new UserTicket(userId,"buy", 1L, lottery.get());
        user_ticketRepository.save(user_ticket);
        return user_ticket.getId().toString();
    }

    public UserTicketResponseDto getUserTicket(String userId) {
        List<Lottery> lotteries = lotteryRepository.findByUserTicketUserId(userId);
        List<String> tickets = lotteries.stream().map(lottery -> lottery.getTicket()).toList();

        if (tickets.isEmpty())
            throw new NotFoundException("No tickets found for user with userId: " + userId);

        Long count = tickets.stream().mapToLong(ticket -> 1).sum();
        Double total = lotteries.stream().mapToDouble(lottery -> lottery.getPrice()).sum();
        return new UserTicketResponseDto(tickets, count, total);
    }

    public String deleteLottery(String userId, String ticketId) {
        Optional<UserTicket> user_tickets = user_ticketRepository.findByUserIdAndTicket(userId, ticketId);

        if (!user_tickets.isPresent())
            throw new NotFoundException("User with userId " + userId + " not found");

        user_ticketRepository.delete(user_tickets.get());
        return ticketId;
    }
}
