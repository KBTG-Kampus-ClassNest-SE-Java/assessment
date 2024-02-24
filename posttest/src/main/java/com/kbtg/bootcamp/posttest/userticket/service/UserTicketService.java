package com.kbtg.bootcamp.posttest.userticket.service;

import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;

public interface UserTicketService {

    String buyLottery(String userId, String ticketId);
    UserTicketResDto getLotteries(String userId);
    String sellLottery(String userId, String ticketId);
}
