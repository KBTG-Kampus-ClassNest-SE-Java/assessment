package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LotteryService lotteryService;

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ButLotteryResponse buyLottery(@PathVariable Integer userId, @PathVariable Integer ticketId) {
        return lotteryService.buyLottery(userId, ticketId);
    }
}
