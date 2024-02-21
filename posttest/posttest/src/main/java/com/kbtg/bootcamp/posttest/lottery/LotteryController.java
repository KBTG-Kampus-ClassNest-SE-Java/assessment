package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.Valid;
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
    @PostMapping("admin/lotteries")
    public ResponseEntity<Map<String, String>> createLottery(@Valid @RequestBody LotteryRequestDto lottery) {
        String createdLottery = lotteryService.createLottery(lottery);
        Map<String, String> response = Collections.singletonMap("ticket", createdLottery);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("admin/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> deleteLottery(@PathVariable String ticketId) {
        String DeletedLottery = lotteryService.deleteLottery(ticketId);
        Map<String, String> response = Collections.singletonMap("ticket", DeletedLottery);

        return ResponseEntity.ok(response);
    }
}

