package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.response.TicketResponse;

public interface LotteryService {

  String createLottery(CreateLotteryRequest createLotteryRequest);

  TicketResponse getAllTickets();

  Boolean checkAmountLottery(String ticket) throws NotFoundException;

  Boolean checkExistTicket(String ticket);
}
