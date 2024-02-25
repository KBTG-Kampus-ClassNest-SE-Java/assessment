package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.entity.UserLottery;

import java.util.List;

public interface LotteryService {

    Lottery addLottery(Lottery lottery);

    List<Lottery> getLotteries();

}
