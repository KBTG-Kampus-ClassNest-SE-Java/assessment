package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/lotteries")
public class LotteryController {
    private LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }
    @GetMapping("")
    public ResponseEntity<Object> getLottery(){
        List<Lottery> lotteries = lotteryService.getAllAvailableLotteries();

        Map<String,List<String>> data = new HashMap<>();
        data.put("tickets", lotteries.stream().map(lottery -> lottery.getTicket()).toList());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
