package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.models.BuyLotteryResponse;
import com.kbtg.bootcamp.posttest.models.LotteryResponse;
import com.kbtg.bootcamp.posttest.models.MyLotteryResponse;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import com.kbtg.bootcamp.posttest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserLotteryController {
    @Autowired
    private final UserService userService;

    @Autowired
    private final LotteryService lotteryService;

    public UserLotteryController(UserService userService, LotteryService lotteryService) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping("/{userId}/lotteries")
    public MyLotteryResponse listAllMyLottery(@PathVariable("userId") Integer userId) {
        return userService.listAllMyLottery(userId);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public BuyLotteryResponse buyLottery(@PathVariable("userId") Integer userId, @PathVariable("ticketId") Integer ticketId) {
        return lotteryService.buyLottery(userId, ticketId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public LotteryResponse sellBackMyLottery(@PathVariable("userId") Integer userId, @PathVariable("ticketId") Integer ticketId) {
        return lotteryService.sellBackMyLottery(userId, ticketId);
    }
}
