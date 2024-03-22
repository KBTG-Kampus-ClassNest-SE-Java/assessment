package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lotteries")
// @crossOrigin * mean let everyone can connect to this api. so irl we should put real url.
//@CrossOrigin("*")
public class LotteryController {
    //@Autowired
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    //@GetMapping("")
    @GetMapping("")
    public ResponseEntity<Map<String, List<String>>> getLottery() {
        return ResponseEntity.ok(Collections.singletonMap("tickets", lotteryService.getLotteries()));
    }
}
