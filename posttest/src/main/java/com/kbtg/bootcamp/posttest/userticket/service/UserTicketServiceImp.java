package com.kbtg.bootcamp.posttest.userticket.service;


import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.repo.UserTicketRepo;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserTicketServiceImp implements  UserTicketService {
    private final UserTicketRepo userTicketRepo;
    private final LotteryRepo lotteryRepo;

    @Autowired
    public UserTicketServiceImp(UserTicketRepo userTicketRepo, LotteryRepo lotteryRepo) {
        this.userTicketRepo = userTicketRepo;
        this.lotteryRepo = lotteryRepo;
    }
    @Override
    public String buyLottery(String userId, String ticketId) {

        Optional<Lottery> lottery = lotteryRepน.findById(ticketId);
        UserTicket userTicket = new UserTicket();

        if (lottery.isEmpty()) {
            throw new RuntimeException("Lottery number not found.");
        }

        lottery.get().setTicket(ticketId);
        userTicket.setUserId(userId);
        userTicket.setLottery(lottery.get());

        return userTicketRepo.save(userTicket).getId().toString();
    }

    @Override
    public UserTicketResDto getLotteries(String userId) {

        //TODO implement getLotteries logic
        return null;
    }

    @Override
    public String sellLottery(String userId, String ticketId) {

        //TODO implement sellLottery logic
        return null;
    }


}
