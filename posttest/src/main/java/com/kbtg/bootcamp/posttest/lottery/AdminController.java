package com.kbtg.bootcamp.posttest.lottery;


import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

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
//    public LotteryResponseDto createLottery(@Valid @RequestBody LotteryRequestDto lotteryRequestDto) throws Exception {
//        return lotteryService.createLottery(lotteryRequestDto);
//    }
    public ResponseEntity<Map<String, String>> createLottery(@Valid @RequestBody LotteryRequestDto lottery) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("ticket", lotteryService.createLottery(lottery)));
    }
}
