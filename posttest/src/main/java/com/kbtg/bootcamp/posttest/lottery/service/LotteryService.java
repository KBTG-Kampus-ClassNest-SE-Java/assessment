package com.kbtg.bootcamp.posttest.lottery.service;


import com.kbtg.bootcamp.posttest.lottery.entity.LotteryClass;

import java.util.List;
import java.util.Optional;

public interface LotteryService {
    List<String> listAllLotteries();

    Optional<LotteryClass> createLottery(int id);
}
