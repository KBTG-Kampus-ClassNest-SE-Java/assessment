package com.kbtg.bootcamp.posttest.userticket.service;

import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketReqDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;

public interface UserTicketService {

    UserTicketReqDto buyLottery(String userId, String ticketId);
    UserTicketResDto getLotteryByUserId(String userId);
    LotteryResponseDto sellLottery(String userId, String ticketId);
}
