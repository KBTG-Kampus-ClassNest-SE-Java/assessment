package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.entity.UserLottery;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserLotteryService {

    UserLottery addUserLottery(UserLottery userLottery);

    ResponseEntity<Object> buyLottery(String userId, String ticketId);

    ResponseEntity<List<UserLottery>> getUserLotteryTickets(String userId);

    ResponseEntity<UserLottery> sellLotteryTicket(String userId, String ticketId);
}
