package com.kbtg.bootcamp.posttest.lottery;


import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.payload.LotteryResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public LotteryResponseDto createLottery(@Validated @RequestBody LotteryRequestDto lotteryRequestDto) throws Exception {
        return lotteryService.createLottery(lotteryRequestDto);
    }
}
