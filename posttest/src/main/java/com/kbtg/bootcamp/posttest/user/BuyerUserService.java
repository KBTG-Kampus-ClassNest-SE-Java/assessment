package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.core.exceptions.UserNotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
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
