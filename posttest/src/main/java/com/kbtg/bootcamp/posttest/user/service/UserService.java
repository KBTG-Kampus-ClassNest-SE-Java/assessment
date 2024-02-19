package com.kbtg.bootcamp.posttest.user.service;

import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import com.kbtg.bootcamp.posttest.lottery.responese.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.user.dto.TicketIdResponse;

public interface UserService {

  TicketIdResponse buyLottery(int ticketId, String userId);

  UserLotteryResponse getLotteriesByUserId(String userId);

  TicketResponse sellLottery(int ticketId, String userId);


}
