package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.exceptions.UserNotFoundException;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.models.MyLotteryResponse;
import com.kbtg.bootcamp.posttest.repositories.UserRepository;
import com.kbtg.bootcamp.posttest.entities.UserTicket;
import com.kbtg.bootcamp.posttest.repositories.UserTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BuyerUserService implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Override
    public MyLotteryResponse listAllMyLottery(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var userTickets = userTicketRepository.findByUserUserId(user.getUserId());
        var lotteries =  userTickets.stream().map(UserTicket::getLottery).toList();
        var tickets = lotteries.stream().map(Lottery::getTicket).distinct().toList();
        var cost = lotteries.stream().map(Lottery::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new MyLotteryResponse(tickets, lotteries.size(), cost);
    }
}
