package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, String>> createdLottery(@Valid @RequestBody LotteryRequestDto lottery) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("ticket", lotteryService.createLottery(lottery)));
    }
}
