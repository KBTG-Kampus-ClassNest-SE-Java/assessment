package com.kbtg.bootcamp.posttest.admin.service;

import com.kbtg.bootcamp.posttest.admin.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;

public interface AdminService {

  TicketResponse createLottery(CreateLotteryRequest createLotteryRequest);


}
