package com.kbtg.bootcamp.posttest.lottery.service;


import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LottoryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.entity.LotteryClass;

import java.util.List;
import java.util.Optional;

public interface LotteryService {
    LotteryListDto listAllLotteries();

    LotteryResponseDto createLottery(LottoryRequestDto lottoryRequestDto);
}
