package com.kbtg.bootcamp.posttest.lottery.controller;


import com.kbtg.bootcamp.posttest.lottery.request.AdminLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/lotteries")
@RequiredArgsConstructor
public class AdminController {

    private final LotteryService lotteryService;

    @PostMapping
    public ResponseEntity<Object> addLottery(@RequestBody AdminLotteryRequest lotteryRequest) {
        return lotteryService.addLottery(lotteryRequest);
    }
}
