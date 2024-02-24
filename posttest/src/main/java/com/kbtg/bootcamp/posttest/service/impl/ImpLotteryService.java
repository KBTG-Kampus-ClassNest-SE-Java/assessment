package com.kbtg.bootcamp.posttest.service.impl;


import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import jdk.jfr.Description;

import java.util.List;
import java.util.Optional;

public interface ImpLotteryService {
    List<LotteryEntity> getAllLottery();

    List<LotteryEntity> getRemainLotteryFromStore();

    LotteryEntity addLotteryToStore(LotteryEntity lotteryEntity);


    @Description("use for update status true/false")
    void updateStatusLottery(String ticketId, boolean status);

    @Description("use for @PostMapping(\"/users/{userid}/lotteries/{ticket}\")")
    List<LotteryEntity> getLotteryEntity(String ticket);

}