package com.kbtg.bootcamp.lottery.service;

import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryPurchaseResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryUserResponseDto;


public interface LotteryService {
    LotteryResponseDto createLottery(LotteryRequestDto lotteryDTO) throws Exception;
    LotteryResponseDto getAllLotteryTickets() throws Exception;
    LotteryPurchaseResponseDto purchaseLotteryTicket(String userId, String ticketNumber) throws Exception;
    LotteryUserResponseDto getUserLotteryTickets(String userId) throws Exception;
    LotteryResponseDto deleteLottery(String userId, String ticketId) throws Exception;

}
