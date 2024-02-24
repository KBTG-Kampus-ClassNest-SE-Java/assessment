package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.payload.LotteryListResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public LotteryListResponseDto getLotteries() {
        return lotteryService.getLotteries();

    }
}
