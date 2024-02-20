package com.kbtg.bootcamp.lottery.controller;

import com.kbtg.bootcamp.lottery.response.LotteryPurchaseResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LotteryService lotteryService;

    @Autowired
    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/{userId}/lotteries/{lotteryNumber}")
    public LotteryPurchaseResponseDto purchaseLotteryTicket(@PathVariable String userId, @PathVariable String lotteryNumber) throws Exception {
        return lotteryService.purchaseLotteryTicket(userId, lotteryNumber);
    }

    // Additional methods as per requirements...
}