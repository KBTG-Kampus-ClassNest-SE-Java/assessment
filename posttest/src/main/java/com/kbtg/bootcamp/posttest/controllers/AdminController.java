package com.kbtg.bootcamp.posttest.controllers;

import com.kbtg.bootcamp.posttest.dto.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.dto.CreateLotteryResponse;
import com.kbtg.bootcamp.posttest.entities.Lottery;
import com.kbtg.bootcamp.posttest.services.LotteryStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryStoreService lotteryStoreService;

    public AdminController(LotteryStoreService lotteryStoreService) {
        this.lotteryStoreService = lotteryStoreService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<CreateLotteryResponse> createLottery( @Validated @RequestBody CreateLotteryRequest createLotteryRequest) {
        Lottery createdLottery = this.lotteryStoreService.createLottery(createLotteryRequest);

        CreateLotteryResponse response = new CreateLotteryResponse(createdLottery.getTicket());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
