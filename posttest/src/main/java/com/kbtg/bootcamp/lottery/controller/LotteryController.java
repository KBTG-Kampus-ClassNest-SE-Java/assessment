package com.kbtg.bootcamp.lottery.controller;


import com.kbtg.bootcamp.lottery.response.LotteryTicketResponse;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("/lotteries")
    public LotteryTicketResponse getLotteries() throws Exception {
        return lotteryService.getAllLotteryTickets();
    }
}
