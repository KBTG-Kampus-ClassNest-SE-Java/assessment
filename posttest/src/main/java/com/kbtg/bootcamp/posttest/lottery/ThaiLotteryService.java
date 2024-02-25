package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThaiLotteryService implements LotteryService {
    @Autowired
    private LotteryRepository lotteryRepository;

    @Override
    public void addLottery(Lottery lottery) {
        lotteryRepository.save(lottery);
    }

    @Override
    public LotteryResponse listLotteries() {
        var tickets = lotteryRepository.findDistinctTickets();

        return new LotteryResponse(tickets);
    }
}
