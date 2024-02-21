// LotteryController.java
package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public LotteryResponse addLottery(@Validated @RequestBody LotteryRequest request) {
        return lotteryService.addLottery(request);
    }
}
