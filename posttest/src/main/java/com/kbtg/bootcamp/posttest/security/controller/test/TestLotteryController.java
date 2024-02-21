package com.kbtg.bootcamp.posttest.security.controller.test;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestLotteryController {
    @Autowired
    private final LotteryService lotteryService;

    public TestLotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping
    public void setup() {

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

    @PostMapping("/{requestedUserId}/lotteries/{requestedTicketId}")
    public String getBuyLotteryPage(
            @PathVariable(name = "requestedUserId") Integer requestedUserId,
            @PathVariable(name = "requestedTicketId") Integer requestedTicketId,
            @RequestBody UserRequest request
    ) {
        return "test";
    }
}
