// UserLotteryController.java
package com.kbtg.bootcamp.posttest.userLottery;

import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserLotteryController {

    private final UserService userService;
    private final LotteryService lotteryService;

    @Autowired
    public UserLotteryController(UserService userService, LotteryService lotteryService) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping("/users/{userId}/lotteries")
    public UserLotteryResponse getUserLotteries(@PathVariable("userId") String userId) {
        List<String> tickets = userService.getUserLotteryTickets(userId);
        int count = tickets.size();
        int cost = count * 80;

        return new UserLotteryResponse(tickets, count, cost);
    }
}
