package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.exception.TicketNotFoundException;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.model.UserTicket;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.request.LotteryRequest;
import com.kbtg.bootcamp.posttest.response.IdResponse;
import com.kbtg.bootcamp.posttest.response.TicketResponse;
import com.kbtg.bootcamp.posttest.response.TicketsResponse;

import com.kbtg.bootcamp.posttest.response.UserTicketResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LotteryService {



    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;
    LotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository){
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public TicketResponse addTicket(LotteryRequest lotteryRequest){


        Lottery ticket = new Lottery(lotteryRequest.getTicket(),lotteryRequest.getAmount(),lotteryRequest.getPrice());

        lotteryRepository.save(ticket);
        return new TicketResponse(ticket.getTicketNumber());
    }

    public TicketsResponse getAllTickets(){
        List<Lottery> tickets =  lotteryRepository.findAll();
        
        List<String> ticketNumber = tickets.stream()
                .map(Lottery::getTicketNumber)
                .collect(Collectors.toList());

        Set<String> tickerAsSet = new HashSet<>(ticketNumber);
        List<String> uniqueTicketNumbers = new ArrayList<>();
        for(String ticket : tickerAsSet){
            uniqueTicketNumbers.add(ticket);
        }

        return new TicketsResponse(uniqueTicketNumbers);
    }

    @Transactional
    public IdResponse buyTicket(String userId,String ticketNumber){
        Optional<Lottery> existTicket = lotteryRepository.findTopByTicketNumberAndAmountGreaterThan(ticketNumber,0);

        if(existTicket.isEmpty()){
            throw new TicketNotFoundException("Ticket Number : " + ticketNumber +" not found");
        }
        Lottery ticket = existTicket.get();

        UserTicket userticket = new UserTicket(userId, ticket.getID(), ticketNumber,ticket.getPrice());
        ticket.setAmount(ticket.getAmount()-1);
        lotteryRepository.save(ticket);
        userTicketRepository.save(userticket);
        return new IdResponse(userticket.getID());
    }

    public UserTicketResponse getAllUserTickets(String userID){

        List<UserTicket> tickets = userTicketRepository.findByUserID(userID);

        List<String> ticket = tickets.stream()
                .map(UserTicket::getTicketNumber)
                .collect(Collectors.toList());

        int totalAmount = tickets.size();

        int totalPrice = tickets.stream()
                .mapToInt(UserTicket::getTicketPrice)
                .sum();
        return new UserTicketResponse(ticket,totalAmount,totalPrice);

    }

    public TicketResponse sellTicketBack(String userId,String ticketNumber){

        Optional<UserTicket> ticket = userTicketRepository.findTopByUserIDAndTicketNumber(userId,ticketNumber);
        if(ticket.isPresent()){

            Optional<Lottery> isticketInStock = lotteryRepository.findById(ticket.get().getLotteryID());

            Lottery ticketInStock = isticketInStock.get();

            ticketInStock.setAmount(ticketInStock.getAmount()+1);

            lotteryRepository.save(ticketInStock);
            userTicketRepository.delete(ticket.get());

            return new TicketResponse(ticket.get().getTicketNumber());
        }throw new TicketNotFoundException("Ticket Number : " + ticketNumber +" not found");

    }



}
