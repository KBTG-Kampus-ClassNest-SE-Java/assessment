package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public List<Lottery> showAllLotteryPage() {
        return
        lotteryService.getAllLotteries();
    }

    @GetMapping("/lotteries")
    public LotteryResponse addNewLotteryTicketToDb(
            @RequestBody AdminRequest request
    ) {
        return
        lotteryService.createLottery(request);
    }

}
