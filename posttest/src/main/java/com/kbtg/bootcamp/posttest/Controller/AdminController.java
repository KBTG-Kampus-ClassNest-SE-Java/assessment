package com.kbtg.bootcamp.posttest.Controller;

import com.kbtg.bootcamp.posttest.Lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.Service.LotteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/lotteries")
    public ResponseEntity<Map<String, String>> createLottery(@Valid @RequestBody LotteryRequestDto lottery) {
        lottery.validate();
        String createdLottery = lotteryService.createLottery(lottery);
        Map<String, String> response = Collections.singletonMap("ticket", createdLottery);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/lotteries/{Id}")
    public ResponseEntity<Map<String, String>> deleteLottery(@PathVariable String Id) {
        String DeletedLottery = lotteryService.deleteLottery(Id);
        Map<String, String> response = Collections.singletonMap("ticket", DeletedLottery);

        return ResponseEntity.ok(response);
    }
}
