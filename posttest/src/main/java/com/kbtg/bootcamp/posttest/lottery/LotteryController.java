// LotteryController.java
package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }


    @GetMapping
    public Lottery getLotteryById(@RequestBody LotteryRequest request){
        return lotteryService.getLotteryById(request.getTicket());
    }
    @PostMapping
    public LotteryResponse addLottery(@Validated @RequestBody LotteryRequest request) {
        return lotteryService.addLottery(request);
    }
}
