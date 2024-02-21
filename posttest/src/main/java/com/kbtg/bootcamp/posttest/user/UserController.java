package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/GET/lotteries")
    public List<Lottery> getLotteryList(){
        return this.userService.getLottery();
    }
}
