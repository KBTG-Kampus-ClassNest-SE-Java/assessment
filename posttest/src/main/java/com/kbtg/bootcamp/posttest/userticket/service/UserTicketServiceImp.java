package com.kbtg.bootcamp.posttest.userticket.service;


import com.kbtg.bootcamp.posttest.exception.LotteryException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.UserTicketException;
import com.kbtg.bootcamp.posttest.userticket.repo.UserTicketRepo;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketReqDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
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

    //EXP03 Buy lottery
    @Override
    @Transactional
    public UserTicketReqDto buyLottery(String userId, String ticketId) {
        Lottery lottery = lotteryRepo.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("This ticketId:"+ticketId+" is not found in database."));
        //check the lottery in database

        //check amount
        if (lottery.getAmount() <= 0) {
            throw new UserTicketException("This ticketId:"+ticketId+"is sold out");
        }

        //require @Transactional
        lottery.setAmount(lottery.getAmount() - 1);//deduce amount
        lotteryRepo.save(lottery); //Save to database

        //instantiate for saving in user_ticket table
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setLottery(lottery);

        //save in user_ticket and return
        return new UserTicketReqDto(userTicketRepo.save(userTicket).getId());
    }


    //EXP04 view all user's ticket
    @Override
    public UserTicketResDto getLotteryByUserId(String userId) {
        UserTicketResDto userTicketResDto = new UserTicketResDto();

        //list all userId by UserId
        List<UserTicket> fromUser = userTicketRepo.findByUserId(userId);
        //get All ticketId to list
        List<String> tickets = fromUser.stream().map(eachUserId -> eachUserId.getLottery().getTicket()).toList();

        //instantiate for return
        userTicketResDto.setTickets(tickets);
        userTicketResDto.setCount(tickets.size());
        userTicketResDto.setTotalPrice(sumPrice(fromUser));

        return userTicketResDto;
    }


    //EXP05 sell lottery
    @Override
    @Transactional
    public LotteryResponseDto sellLottery(String userId, String ticketId) {
        //get list from user and ticket
        List<UserTicket> userTicket = userTicketRepo.findByUserIdAndTicketId(userId, ticketId);

        //check if the list is empty
        if (userTicket.isEmpty()) {
            throw new UserTicketException("Either userId or ticketId is invalid. Please check.");
        }

        //delete from database
        userTicketRepo.delete(userTicket.get(0));

        //update and return Lottery
        Lottery lottery = lotteryRepo.findById(ticketId)
                .orElseThrow(() -> new LotteryException("The ticket number:"+ticketId+"is not currently existing in database")); //Not sure if it should work like this
        //if

        lottery.setAmount(lottery.getAmount() + 1);
        lotteryRepo.save(lottery);
        return new LotteryResponseDto(ticketId);
    }

//for sum count
    private Integer sumPrice(List<UserTicket> fromUser) {
        Integer sum = 0;
        for (UserTicket ticket : fromUser) {
            sum += ticket.getLottery().getPrice();
        }
        return sum;
    }
}




