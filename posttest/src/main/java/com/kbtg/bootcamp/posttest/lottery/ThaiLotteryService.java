package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThaiLotteryService implements LotteryService {
    @Autowired
    private LotteryRepository lotteryRepository;

    @Override
    public Lottery addLottery(Lottery lottery) {
        return lotteryRepository.save(lottery);
    }
}
