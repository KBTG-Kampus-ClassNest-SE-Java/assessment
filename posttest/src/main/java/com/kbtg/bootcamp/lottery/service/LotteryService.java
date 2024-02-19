package com.kbtg.bootcamp.lottery.service;

import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponse;


public interface LotteryService {
    LotteryResponse createLottery(LotteryRequestDto lotteryDTO);
    LotteryResponse getAllLotteryTickets() throws Exception;
}
