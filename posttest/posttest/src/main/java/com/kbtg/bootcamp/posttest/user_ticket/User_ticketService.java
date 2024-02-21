package com.kbtg.bootcamp.posttest.user_ticket;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import org.springframework.stereotype.Service;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class User_ticketService {
    private final User_ticketRepository user_ticketRepository;
    private final LotteryRepository lotteryRepository;

    public User_ticketService(User_ticketRepository user_ticketRepository, LotteryRepository lotteryRepository) {
        this.user_ticketRepository = user_ticketRepository;
        this.lotteryRepository = lotteryRepository;
    }

    public String BuyTicket(String userId, String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicket(ticketId);

        if (lottery.isPresent()) {
            User_ticket user_ticket = new User_ticket(userId,"buy", 1, lottery.get());
            user_ticketRepository.save(user_ticket);
            return user_ticket.getId().toString();
        } else {
            // Handle case where the lottery with the given ticketId is not found
            throw new NoSuchElementException("Lottery with ticketId " + ticketId + " not found");
        }
    }

    public User_ticketResponseDto getUserTicket(String userId) {
        List<Lottery> lotteries = lotteryRepository.findByUserTicketUserId(userId);
        List<String> tickets = lotteries.stream().map(lottery -> lottery.getTicket()).toList();

        if (!tickets.isEmpty()) {
            Integer count = tickets.size();
            Float total = (float) lotteries.stream().mapToDouble(lottery -> lottery.getPrice()).sum();
            return new User_ticketResponseDto(tickets, count, total);
        } else {
            // Handle case where the user with the given userId is not found
            throw new NoSuchElementException("User with userId " + userId + " not found");
        }
    }

    public String deleteLottery(String userId, String ticketId) {
        Optional<User_ticket> user_tickets = user_ticketRepository.findByUserIdAndTicket(userId, ticketId);

        if (user_tickets.isPresent()) {
            user_ticketRepository.delete(user_tickets.get());
            return ticketId;
        } else {
            // Handle case where the user with the given userId is not found
            throw new NoSuchElementException("User with userId " + userId + " not found");
        }
    }
}
