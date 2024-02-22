package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lotteries")
public class AdminLotteryController {

    private final ImpLotteryService impLotteryService;

    public AdminLotteryController(ImpLotteryService impLotteryService) {
        this.impLotteryService = impLotteryService;
    }

    @GetMapping("")
    public List<LotteryEntity> findAllLottery() {
        return impLotteryService.getAllLottery();
    }

    @PostMapping("")
    public LotteryEntity addLotteryToStore(@RequestBody LotteryEntity lotteryEntity) {
        impLotteryService.addLotteryToStore(lotteryEntity);
        return lotteryEntity;
    }

//    @GetMapping("/{id}")
//    public Optional<LotteryEntity> findLotteryById(@PathVariable("id") Long id) {
//        return ILotteryService.findLotteryById(id);
//    }

//    @PutMapping("")
//    public LotteryEntity updateLottery(@RequestBody LotteryEntity lotteryEntity) {
//        return ILotteryService.updateLottery(lotteryEntity);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteLotteryById(@PathVariable("id") Long id) {
//        impLotteryService.deleteLottery(id);
//    }
}
