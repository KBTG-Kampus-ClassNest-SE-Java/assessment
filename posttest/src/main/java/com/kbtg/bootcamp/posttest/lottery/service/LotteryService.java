package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicket;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketListResponse;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketRequest;
import com.kbtg.bootcamp.posttest.lottery.model.LotteryTicketResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryService {

    private final LotteryTicketRepository lotteryTicketRepository;

    @Autowired
    public LotteryService(LotteryTicketRepository lotteryTicketRepository) {
        this.lotteryTicketRepository = lotteryTicketRepository;
    }

    public LotteryTicketResponse createLotteryTicket(LotteryTicketRequest request) {
        LotteryTicket existingTicket = lotteryTicketRepository.findByTicket(request.getTicket());
        if (existingTicket != null) {
            return new LotteryTicketResponse(existingTicket.getTicket());
        } else {
            LotteryTicket ticket = new LotteryTicket();
            ticket.setTicket(request.getTicket());
            ticket.setPrice(request.getPrice());
            ticket.setAmount(request.getAmount());

            LotteryTicket savedTicket = lotteryTicketRepository.save(ticket);

            return new LotteryTicketResponse(savedTicket.getTicket());
        }
    }

    public LotteryTicketListResponse getLotteryTicketList() {
        List<String> ticketNumbers = lotteryTicketRepository
                .findAll()
                .stream()
                .map(LotteryTicket::getTicket)
                .collect(Collectors.toList());

        return new LotteryTicketListResponse(ticketNumbers);
    }
}
