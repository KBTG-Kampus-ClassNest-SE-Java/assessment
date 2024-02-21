package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.model.Lottery;
import com.kbtg.bootcamp.posttest.repositoty.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryRepository lotteryRepository;

    private final LotteryService lotteryService;

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> getAllLotteries(){

        return lotteryService.getAllLotteries();
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<LotteryResponseDto> createLottery(@RequestBody LotteryRequestDto requestDto){
        return lotteryService.createLottery(requestDto);
    }

}
