package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotteryService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Transactional
    public List<String> getLottery() {
        List<String> lotteries = lotteryRepository.findAll()
                .stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList());
        return lotteries;
    }

    @Transactional
    public String addLottery(LotteryRequestDto requestDto) {
        Lottery lottery = new Lottery();
        lottery.setTicket(requestDto.getTicket_id());
        lottery.setPrice(requestDto.getPrice());
        lottery.setAmount(requestDto.getAmount());
        lotteryRepository.save(lottery);
        return lottery.getTicket();
    }
}
