package com.kbtg.bootcamp.posttest.Controller;

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
@RequestMapping("")
public class LotteryController {

    @Autowired
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("/test")
    public String hello() {
        return "Hello, ";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/lotteries")
    public ResponseEntity<Map<String, List<String>>> getLottery() {
        Map<String, List<String>> response = Collections.singletonMap("ticket", lotteryService.getLottery());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("admin/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> deleteLottery(@PathVariable String ticketId) {
        String DeletedLottery = lotteryService.deleteLottery(ticketId);
        Map<String, String> response = Collections.singletonMap("ticket", DeletedLottery);

        return ResponseEntity.ok(response);
    }
}

