package com.kbtg.bootcamp.lottery.service.impl;

import com.kbtg.bootcamp.lottery.entity.Lottery;
import com.kbtg.bootcamp.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponse;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {
    private final LotteryRepository lotteryRepository;

    @Autowired
    public LotteryServiceImpl(LotteryRepository lotteryRepository) throws Exception {
        this.lotteryRepository = lotteryRepository;
    }

    @Override
    public LotteryResponse createLottery(LotteryRequestDto lotteryDTO) {
        Lottery lottery = new Lottery();
        lottery.setTicketNumber(lotteryDTO.getTicketNumber());
        lottery.setTicketPrice(lotteryDTO.getTicketPrice());
        lottery.setTicketAmount(lotteryDTO.getTicketAmount());
        lotteryRepository.save(lottery);
        return new LotteryResponse(lottery.getTicketNumber());
    }

    @Override
    public LotteryResponse getAllLotteryTickets() throws Exception {
        List<String> ticketNumbers = lotteryRepository.findAll().stream()
                .map(Lottery::getTicketNumber)
                .distinct()
                .toList();
        return new LotteryResponse(ticketNumbers);
    }
}
