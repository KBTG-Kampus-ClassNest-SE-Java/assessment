package com.kbtg.bootcamp.posttest.lottery.controller;


import com.kbtg.bootcamp.posttest.lottery.Respone.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lotteries")
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping
    public ResponseEntity<LotteryResponse> getAllLotteryTickets() {
        return lotteryService.getAllLotteries();
    }
}
