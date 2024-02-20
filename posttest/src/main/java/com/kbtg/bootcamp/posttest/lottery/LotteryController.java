// LotteryController.java
package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }


    @PostMapping
    public ResponseEntity<LotteryResponse> addLottery(@RequestBody LotteryRequest request) {
        String lottery = lotteryService.addLottery(request);

        // Create a response body containing the lottery ticket
        LotteryResponse response = new LotteryResponse(lottery);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
