package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.entity.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.service.LotteryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> createLottery(@RequestBody LotteryRequestDto requestDto) throws  Exception{
        return lotteryService.createLottery(requestDto);
    }

}
