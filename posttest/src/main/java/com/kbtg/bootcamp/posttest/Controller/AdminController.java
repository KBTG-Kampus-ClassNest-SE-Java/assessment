package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.Lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.Lottery.LotteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/lotteries")
    public ResponseEntity<Map<String, String>> createLottery(@Valid @RequestBody LotteryRequestDto lottery) {
        String createdLottery = lotteryService.createLottery(lottery);
        Map<String, String> response = Collections.singletonMap("ticket", createdLottery);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
