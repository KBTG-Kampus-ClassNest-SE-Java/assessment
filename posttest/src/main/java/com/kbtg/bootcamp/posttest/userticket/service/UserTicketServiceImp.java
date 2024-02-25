package com.kbtg.bootcamp.posttest.userticket.service;


import com.kbtg.bootcamp.posttest.exception.LotteryException;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.UserTicketException;
import com.kbtg.bootcamp.posttest.userticket.repo.UserTicketRepo;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketReqDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public UserTicketReqDto buyLottery(String userId, String ticketId) {

        Optional<Lottery> lottery = lotteryRepo.findById(ticketId);
        UserTicket userTicket = new UserTicket();

        if (lottery.isEmpty()) {
            throw new LotteryException("This lottery number is not available for purchase.");
        }

        lottery.get().setTicket(ticketId);
        userTicket.setUserId(userId);
        userTicket.setLottery(lottery.get());

        return new UserTicketReqDto(userTicketRepo.save(userTicket).getId());
    }

    @Override
    public UserTicketResDto getLotteryByUserId(String userId) {
        UserTicketResDto userTicketResDto = new UserTicketResDto();
        List<UserTicket> fromUser = userTicketRepo.findByUserId(userId);
        List<String> tickets = fromUser.stream().map(eachUserId -> eachUserId.getLottery().getTicket()).toList();

        userTicketResDto.setTickets(tickets);
        userTicketResDto.setCount(tickets.size());
        userTicketResDto.setTotalPrice(sumPrice(fromUser));

        return userTicketResDto;
    }


    @Override
    public LotteryResponseDto sellLottery(String userId, String ticketId) {
        List<UserTicket> userTicket = userTicketRepo.findByUserIdAndTicketId(userId, ticketId);

        if (userTicket.isEmpty()) {
            throw new UserTicketException("Either userId or ticketId is invalid. Please check.");
        }

        // Delete UserTicket
        userTicketRepo.delete(userTicket.get(0));

        // Update and return Lottery
        Lottery lottery = lotteryRepo.findById(ticketId)
                .orElseThrow(() -> new UserTicketException("Either userId or ticketId is invalid. Please check.")); // Reuse exception

        lottery.setAmount(1);
        lotteryRepo.save(lottery);
        return new LotteryResponseDto(ticketId);
    }


    private Integer sumPrice(List<UserTicket> fromUser) {
        Integer sum = 0;
        for (UserTicket ticket : fromUser) {
            sum += ticket.getLottery().getPrice();
        }
        return sum;
    }
}




