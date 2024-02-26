package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.dto.GetLotteriesByUserIdResponse;
import com.kbtg.bootcamp.posttest.entities.UserTicket;

public interface UserTicketService {
    UserTicket buyLottery(String userId, String ticketId);
    GetLotteriesByUserIdResponse getLotteriesByUserId(String userId);
    String sellLottery(String userId, String ticketId);
}
