package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private LotteryService lotteryService;

    @PostMapping("/lotteries")
    public ResponseEntity<Lottery> addLottery(@Valid @RequestBody Lottery lottery) {
        lotteryService.addLottery(lottery);

        return new ResponseEntity<>(lottery, HttpStatus.CREATED);
    }
}
