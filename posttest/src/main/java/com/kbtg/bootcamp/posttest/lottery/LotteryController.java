package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService adminService) {
        this.lotteryService = adminService;
    }

    @PostMapping("/admin/lotteries")
    public LotteryResponseDto createLottery(@Validated @RequestBody LotteryRequestDto requestDto){
        return this.lotteryService.createLottery(requestDto);
    }
}
