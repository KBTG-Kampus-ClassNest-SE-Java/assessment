// LotteryController.java
package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("/list")
    public LotteryListResponse getAllLotteryTickets() {
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        List<Lottery> lotteries = lotteryService.getAllLotteries();
        List<String> listOfLottery = lotteries.stream()
                .map(Lottery::getIdAsString)
                .collect(Collectors.toList());
        return new LotteryListResponse(listOfLottery);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LotteryResponse addLottery(@Validated @RequestBody LotteryRequest request) throws Exception {
        return lotteryService.addLottery(request);
    }

    @DeleteMapping
    public void DeleteLottery() {
        lotteryService.deleteAllLotteries();
    }
}
