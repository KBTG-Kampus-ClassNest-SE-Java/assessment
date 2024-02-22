package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {

    private final LotteryService adminService;

    public LotteryController(LotteryService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin/lotteries")
    public Lottery createLottery(@Validated @RequestBody LotteryRequestDto requestDto){
        return this.adminService.createLottery(requestDto);
    }
}
