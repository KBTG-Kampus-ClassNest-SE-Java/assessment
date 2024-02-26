package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public Map<String, List<String>> getLottery() {
        List<String> lotteries = this.lotteryRepository.findAll().stream().map(Lottery::getTicket).collect(Collectors.toList());

        Map<String, List<String>> allTicket = new HashMap<>();
        allTicket.put("ticket" ,lotteries);
        return allTicket;
    }

}
