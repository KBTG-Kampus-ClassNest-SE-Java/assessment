package com.kbtg.bootcamp.posttest.lottery;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LotteryService {
    private final LotteryRepository ticketRepository;

    public LotteryService(LotteryRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void addTicket(LotteryRequest lotteryRequest){
        Lottery ticket = new Lottery(   lotteryRequest.ticket(),
                                        lotteryRequest.price(),
                                        lotteryRequest.amount()
                                    );
        ticketRepository.save(ticket);
    }

    public List<Lottery> getAllLotteries(){
        return ticketRepository.findAll();
    }
}
