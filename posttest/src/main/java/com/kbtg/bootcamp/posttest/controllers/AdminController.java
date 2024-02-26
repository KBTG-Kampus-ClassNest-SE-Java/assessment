package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.dto.CreateLotteryResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<CreateLotteryResponse> createLottery( @Validated @RequestBody CreateLotteryRequest createLotteryRequest) {
        Lottery createdLottery = this.lotteryService.createLottery(createLotteryRequest);

        CreateLotteryResponse response = new CreateLotteryResponse(createdLottery.getTicket());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
