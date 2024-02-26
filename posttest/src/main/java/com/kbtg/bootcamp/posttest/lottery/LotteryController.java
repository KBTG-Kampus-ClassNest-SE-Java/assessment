package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService adminService) {
        this.lotteryService = adminService;
    }

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryListResponseDto> getLotteryList() {
        try {
            LotteryListResponseDto response = lotteryService.getAllLotteries();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            throw new InternalServiceException("Internal service exception with Normal service");
        }
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<LotteryResponseDto> createLottery(@Validated @RequestBody LotteryRequestDto requestDto){
        try {
            LotteryResponseDto response = lotteryService.createLottery(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception) {
            throw new InternalServiceException("Internal service exception with Normal service");
        }
    }

}
