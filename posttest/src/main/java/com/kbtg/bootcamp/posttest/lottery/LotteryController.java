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


    @GetMapping("/{id}")
    public LotteryResponse getLotteryById(@PathVariable("id") String id){
        return lotteryService.getLotteryById(id);
    }

    @PostMapping
    public LotteryResponse addLottery(@Validated @RequestBody LotteryRequest request) throws Exception {
        return lotteryService.addLottery(request);
    }

    @DeleteMapping
    public void DeleteLottery() {
        lotteryService.deleteAllLotteries();
    }
}
