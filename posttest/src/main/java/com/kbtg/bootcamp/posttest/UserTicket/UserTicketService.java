package com.kbtg.bootcamp.posttest.UserTicket;

import com.kbtg.bootcamp.posttest.Entity.Lottery;
import com.kbtg.bootcamp.posttest.Entity.UserTicket;
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

        if (lottery.isPresent()) {
            UserTicket user_ticket = new UserTicket(userId,"buy", 1, lottery.get());
            user_ticketRepository.save(user_ticket);
            return user_ticket.getId().toString();
        } else {
            // Handle case where the lottery with the given ticketId is not found
            throw new NoSuchElementException("Lottery with ticketId " + ticketId + " not found");
        }
    }

    public UserTicketResponseDto getUserTicket(String userId) {
        List<Lottery> lotteries = lotteryRepository.findByUserTicketUserId(userId);
        List<String> tickets = lotteries.stream().map(lottery -> lottery.getTicket()).toList();

        if (!tickets.isEmpty()) {
            Integer count = tickets.size();
            Float total = (float) lotteries.stream().mapToDouble(lottery -> lottery.getPrice()).sum();
            return new UserTicketResponseDto(tickets, count, total);
        } else {
            // Handle case where the user with the given userId is not found
            throw new NoSuchElementException("User with userId " + userId + " not found");
        }
    }

    public String deleteLottery(String userId, String ticketId) {
        Optional<UserTicket> user_tickets = user_ticketRepository.findByUserIdAndTicket(userId, ticketId);

        if (user_tickets.isPresent()) {
            user_ticketRepository.delete(user_tickets.get());
            return ticketId;
        } else {
            // Handle case where the user with the given userId is not found
            throw new NoSuchElementException("User with userId " + userId + " not found");
        }
    }
}
