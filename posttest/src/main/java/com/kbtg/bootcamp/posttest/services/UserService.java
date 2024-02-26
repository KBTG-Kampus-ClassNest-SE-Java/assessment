package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.models.MyLotteryResponse;

public interface UserService {
    MyLotteryResponse listAllMyLottery(Integer userId);
}
