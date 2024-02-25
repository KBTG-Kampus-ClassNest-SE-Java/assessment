package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.stereotype.Service;

@Service
public interface LotteryService {
    public void addLottery(Lottery lottery);
    public LotteryResponse listLotteries();
}
