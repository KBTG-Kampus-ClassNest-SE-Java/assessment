package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public List<LotteryEntity> findAllLottery() {
        return lotteryService.findAllLottery();
    }

    @GetMapping("/{id}")
    public Optional<LotteryEntity> findLotteryById(@PathVariable("id") Long id) {
        return lotteryService.findLotteryById(id);

    }
    @PostMapping("")
    public LotteryEntity saveLottery(@RequestBody LotteryEntity lotteryEntity) {
        return lotteryService.saveLottery(lotteryEntity);
    }


    @PutMapping("")
    public LotteryEntity updateLottery(@RequestBody LotteryEntity lotteryEntity) {
        return lotteryService.updateLottery(lotteryEntity);
    }


    @DeleteMapping("/{id}")
    public void deleteLotteryById(@PathVariable("id")Long id) {
        lotteryService.deleteLottery(id);
    }
}
