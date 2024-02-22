package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    @Autowired
    private final LotteryRepo lotteryRepo;

    public LotteryService(LotteryRepo lotteryRepo) {
        this.lotteryRepo = lotteryRepo;
    }




}
