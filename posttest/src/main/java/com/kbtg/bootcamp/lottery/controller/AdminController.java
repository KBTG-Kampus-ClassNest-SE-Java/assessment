package com.kbtg.bootcamp.lottery.controller;

import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    @Autowired
    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }


    @PostMapping("/lotteries")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LotteryResponseDto> createLottery(@Validated @RequestBody LotteryRequestDto lotteryDTO) throws Exception {
        LotteryResponseDto createdLottery = lotteryService.createLottery(lotteryDTO);
        return  ResponseEntity.status(201).body(createdLottery);
    }

}
