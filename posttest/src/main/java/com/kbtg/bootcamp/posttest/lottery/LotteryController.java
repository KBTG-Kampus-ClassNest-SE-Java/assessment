package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class LotteryController {

    @Autowired
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("/lotteries")
    public ResponseEntity<Object> getLotteries(){
        List<String> tickets = lotteryService.getLottery();
        LotteryResponse lotteryResponse = new LotteryResponse(tickets);
        return new ResponseEntity<>(lotteryResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/lotteries")
    public String  addLotteries(@RequestBody LotteryRequestDto requestDto){
        return lotteryService.addLottery(requestDto);
    }
}
