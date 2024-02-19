package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestLotteryController {
    @Autowired
    private final LotteryService lotteryService;

    public TestLotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("/{requestedId}")
    public Lottery getLotteryPage(
            @PathVariable Long requestedId
    ) {
        Optional<Lottery> lottery = lotteryService.getLottery(requestedId);
        if (lottery.isPresent()) {
            return lottery.get();
        } else {
            throw new RuntimeException();
        }
    }
}
