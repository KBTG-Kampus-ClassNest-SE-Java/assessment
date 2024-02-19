package com.kbtg.bootcamp.lottery.service;

import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryTicketResponse;


public interface LotteryService {
    LotteryTicketResponse createLottery(LotteryRequestDto lotteryDTO);
    LotteryTicketResponse getAllLotteryTickets() throws Exception;
}
