package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.models.LotteriesResponse;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lotteries")
public class LotteryController {
    @Autowired
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public LotteriesResponse listLotteries() {
        return lotteryService.listLotteries();
    }
}
