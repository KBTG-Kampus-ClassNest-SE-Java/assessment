package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.user.UserTicket;
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

    @PostMapping("/admin/lotteries")
    public LotteryResponseDto createLottery(@Validated @RequestBody LotteryRequestDto requestDto){
        try {
            LotteryResponseDto response = lotteryService.createLottery(requestDto);
            return new ResponseEntity<>(response, HttpStatus.OK).getBody();
        } catch (Exception exception) {
            throw new InternalServiceException("Internal service exception with Normal service");
        }
    }

}
