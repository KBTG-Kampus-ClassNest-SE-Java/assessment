package com.kbtg.bootcamp.posttest.user.service;

import com.kbtg.bootcamp.posttest.exception.ResourceUnavailableException;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryTicketRepository;
import com.kbtg.bootcamp.posttest.user.model.User;
import com.kbtg.bootcamp.posttest.user.model.UserTicketResponse;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LotteryTicketRepository lotteryTicketRepository;
    private final UserTicketService userTicketService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            LotteryTicketRepository lotteryTicketRepository,
            UserTicketService userTicketService
    ) {
        this.userRepository = userRepository;
        this.lotteryTicketRepository = lotteryTicketRepository;
        this.userTicketService = userTicketService;
    }

    @Transactional
    public UserTicketResponse purchaseLotteryTicket(String userId, String ticketId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new ResourceUnavailableException("userId: " + userId + " not found");
        }

        LotteryTicket lotteryTicket = lotteryTicketRepository.findByTicket(ticketId);
        if (lotteryTicket == null || lotteryTicket.getAmount() <= 0) {
            throw new ResourceUnavailableException("ticketId: " + ticketId + " unavailable (not found or out of stock)");
        }

        UserTicketResponse userTicket = userTicketService.createUserTicketTransaction(user, lotteryTicket);

        this.updateUserActivity(user, lotteryTicket.getPrice());
        
        lotteryTicket.setAmount(lotteryTicket.getAmount() - 1);
        lotteryTicketRepository.save(lotteryTicket);

        return new UserTicketResponse(userTicket.id());
    }

    @Transactional
    protected void updateUserActivity(User user, int price) {
        user.setTotalSpent(user.getTotalSpent() + price);
        user.setTotalLottery(user.getTotalLottery() + 1);
        userRepository.save(user);
    }

}

