package com.kbtg.bootcamp.posttest.service.impl;


import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;

import java.util.List;
import java.util.Optional;

public interface ImpLotteryService {
    List<LotteryEntity> getAllLottery();

    List<LotteryEntity> getRemainLotteryFromStore();

    LotteryEntity addLotteryToStore(LotteryEntity lotteryEntity);

    void updateStatusLottery(String ticketId, boolean status);

    List<LotteryEntity> getLotteryEntity(String ticket);

}