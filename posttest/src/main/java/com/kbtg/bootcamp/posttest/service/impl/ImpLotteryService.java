package com.kbtg.bootcamp.posttest.service.impl;


import com.kbtg.bootcamp.posttest.entity.LotteryEntity;

import java.util.List;
import java.util.Optional;

public interface ImpLotteryService {
    List<LotteryEntity> findAllLottery();
    Optional<LotteryEntity> findLotteryById(Long id);
    LotteryEntity saveLottery(LotteryEntity lotteryEntity);
//    LotteryEntity updateLottery(LotteryEntity lotteryEntity);
    void deleteLottery(Long id);

    void updateLottery(String ticketId, boolean active);

}
