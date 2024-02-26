package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/lotteries")
    public ResponseEntity<?> addNewLotteryTicketToDb(
            @RequestBody AdminRequest request
    ) {
        return
        lotteryService.createLottery(request);
    }

}
