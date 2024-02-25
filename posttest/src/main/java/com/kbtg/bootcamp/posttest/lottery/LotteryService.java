package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.stereotype.Service;

@Service
public interface LotteryService {
    public Lottery addLottery(Lottery lottery);
}
