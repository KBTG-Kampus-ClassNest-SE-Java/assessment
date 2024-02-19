package com.kbtg.bootcamp.posttest.features.lottery;

import com.kbtg.bootcamp.posttest.features.lottery.model.get_all_lottery.GetAllLotteryResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public ResponseEntity<GetAllLotteryResDto> getAllLotteries() {
        final GetAllLotteryResDto bodyRes = this.lotteryService.getAllLotteries();

        return ResponseEntity.ok(bodyRes);
    }
}
