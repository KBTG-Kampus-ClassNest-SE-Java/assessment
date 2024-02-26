package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.entities.UserTicket;

import java.util.List;

public interface LotteryService {
    Lottery createLottery(CreateLotteryRequest lottery);
    List<Lottery> getAvailableLotteries();
    UserTicket buyLottery(String userId, String ticketId);
    GetLotteriesByUserIdResponse getLotteriesByUserId(String userId);
    String sellLottery(String userId, String ticketId);
}
