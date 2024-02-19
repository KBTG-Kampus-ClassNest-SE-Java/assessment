package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.responese.LotteryListResponse;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import com.kbtg.bootcamp.posttest.lottery.responese.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;

public interface LotteryService {

  TicketResponse createLottery(CreateLotteryRequest createLotteryRequest);

  LotteryListResponse getLotteries();

  TicketResponse buyLottery(int ticketId, int userId);

  UserLotteryResponse getLotteriesByUserId(int userId);

  TicketResponse sellLottery(int ticketId, int userId);
}
