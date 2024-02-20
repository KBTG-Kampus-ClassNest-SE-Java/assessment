package com.kbtg.bootcamp.lottery.controller;

import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public LotteryResponseDto createLottery(@Valid @RequestBody LotteryRequestDto lotteryDTO) throws Exception {
        return lotteryService.createLottery(lotteryDTO);
    }

}
