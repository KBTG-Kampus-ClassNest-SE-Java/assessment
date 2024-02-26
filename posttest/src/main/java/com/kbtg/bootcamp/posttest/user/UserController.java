package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/{userId}/lotteries")
    public MyLotteryResponse listAllMyLottery(@PathVariable("userId") Integer userId) {
        return userService.listAllMyLottery(userId);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public BuyLotteryResponse buyLottery(@PathVariable("userId") Integer userId, @PathVariable("ticketId") Integer ticketId) {
        return lotteryService.buyLottery(userId, ticketId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public Lottery sellBackMyLottery(@PathVariable("userId") Integer userId, @PathVariable("ticketId") Integer ticketId) {
        return lotteryService.sellBackMyLottery(userId, ticketId);
    }
}
