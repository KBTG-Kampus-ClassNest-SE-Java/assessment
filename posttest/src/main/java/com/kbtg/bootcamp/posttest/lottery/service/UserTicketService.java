package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.request.SellLotteryResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserTicketService {

    ResponseEntity<Object> buyLottery(String userId, String ticketId);

    ResponseEntity<Map<String, Object>> getUserLotteryTickets(String userId);

    ResponseEntity<SellLotteryResponse> sellLotteryTicket(String userId, String ticketId);
}
