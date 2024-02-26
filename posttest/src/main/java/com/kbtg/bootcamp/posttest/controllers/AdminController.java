package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import com.kbtg.bootcamp.posttest.models.LotteryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private LotteryService lotteryService;

    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponse> addLottery(@Valid @RequestBody Lottery lottery) {
        var lotteryResponse = lotteryService.addLottery(lottery);

        return new ResponseEntity<>(lotteryResponse, HttpStatus.CREATED);
    }
}
