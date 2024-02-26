package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.shared.responses.LotteryResponse;
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
public class AddLotteryController {
    @Autowired
    private LotteryService lotteryService;

    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponse> addLottery(@Valid @RequestBody Lottery lottery) {
        var lotteryResponse = lotteryService.addLottery(lottery);

        return new ResponseEntity<>(lotteryResponse, HttpStatus.CREATED);
    }
}
