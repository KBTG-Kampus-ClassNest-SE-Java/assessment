package com.kbtg.bootcamp.posttest.lottery.controller;


import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lotteries")
@RequiredArgsConstructor
public class AdminController {

    private final LotteryService lotteryService;

    @PostMapping
    public ResponseEntity<Lottery> addLottery(@RequestBody Lottery lottery) {
        return new ResponseEntity<>(lotteryService.addLottery(lottery), HttpStatus.CREATED);

    }

}
