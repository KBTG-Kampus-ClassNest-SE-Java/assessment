package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private LotteryService lotteryService;

    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("lotteries")
    public ResponseEntity<Object> getLottery(){
        List<Lottery> lotteries = lotteryService.getAllLotteries();
        Map<String,List<Lottery>> data = new HashMap<>();
        data.put("tickets", lotteries);
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

}
