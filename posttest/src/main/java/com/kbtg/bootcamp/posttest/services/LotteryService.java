package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.entities.Lottery;

import java.util.List;

public interface LotteryService {
    Lottery createLottery(CreateLotteryRequest lottery);
    List<Lottery> getLotteries();
}
