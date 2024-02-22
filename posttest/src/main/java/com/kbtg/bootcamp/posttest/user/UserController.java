package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/lotteries")
    public LotteryListResponseDto getLotteryList(){
        return this.userService.getAllLotteries();
    }


}
