package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.entity.UserLottery;
import com.kbtg.bootcamp.posttest.lottery.service.UserLotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/lotteries")
public class UserLotteryController {


    private final UserLotteryService userLotteryService;

    @PostMapping("/{ticketId}")
    public ResponseEntity<Object> buyLottery(@PathVariable String userId, @PathVariable String ticketId) {
        return userLotteryService.buyLottery(userId, ticketId);
    }

    @GetMapping
    public ResponseEntity<List<UserLottery>> getUserPurchasedLotteries(@PathVariable String userId) {
        return userLotteryService.getUserLotteryTickets(userId);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<UserLottery> sellLottery(@PathVariable String userId, @PathVariable String ticketId) {
        return userLotteryService.sellLotteryTicket(userId, ticketId);
    }
}
