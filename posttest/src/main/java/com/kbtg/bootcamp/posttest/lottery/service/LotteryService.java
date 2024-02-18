package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    private final LotteryTicketRepository lotteryTicketRepository;

    @Autowired
    public LotteryService(LotteryTicketRepository lotteryTicketRepository) {
        this.lotteryTicketRepository = lotteryTicketRepository;
    }

    public LotteryTicketResponse createLotteryTicket(LotteryTicketRequest request) {
        LotteryTicket ticket = new LotteryTicket();
        ticket.setTicket(request.getTicket());
        ticket.setPrice(request.getPrice());
        ticket.setAmount(request.getAmount());

        LotteryTicket savedTicket = lotteryTicketRepository.save(ticket);

        return new LotteryTicketResponse(savedTicket.getTicket());
    }
}
