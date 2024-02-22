package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.Exception.NotFoundException;
import com.kbtg.bootcamp.posttest.Lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.Lottery.LotteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    @Autowired
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<String>>> getLottery() {
        List<String> AllLotteries = lotteryService.getLottery();
        if (AllLotteries.isEmpty()) {
            throw new NotFoundException("Lotteries not found");
        }
        Map<String, List<String>> response = Collections.singletonMap("ticket", AllLotteries);

        return ResponseEntity.ok(response);
    }
}

