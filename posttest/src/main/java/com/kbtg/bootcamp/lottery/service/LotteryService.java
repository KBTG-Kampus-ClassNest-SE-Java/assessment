package com.kbtg.bootcamp.lottery.service;

import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryPurchaseResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;


public interface LotteryService {
    LotteryResponseDto createLottery(LotteryRequestDto lotteryDTO) throws Exception;
    LotteryResponseDto getAllLotteryTickets() throws Exception;
    LotteryPurchaseResponseDto purchaseLotteryTicket(String userId, String ticketNumber) throws Exception;
}
