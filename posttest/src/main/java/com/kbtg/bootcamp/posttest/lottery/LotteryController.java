// LotteryController.java
package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping
    public LotteryResponse showLotteryList() {
        List<Lottery> lotteries = lotteryService.getAllLotteries();
        List<String> ticketNumbers = lotteries.stream()
                .map(Lottery::getIdAsString)
                .collect(Collectors.toList());
        return new LotteryResponse(ticketNumbers);
    }

    @DeleteMapping
    public void DeleteLottery() {
        lotteryService.deleteAllLotteries();
    }
}
