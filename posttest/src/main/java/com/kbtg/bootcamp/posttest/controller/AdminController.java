package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> createLottery(@Valid @RequestBody LotteryRequestDto requestDto){
        return lotteryService.createLottery(requestDto);
    }

}
