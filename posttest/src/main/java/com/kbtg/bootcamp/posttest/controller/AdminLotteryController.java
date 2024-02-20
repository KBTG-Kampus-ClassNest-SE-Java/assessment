package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lotteries")
public class AdminLotteryController {

    private final ImpLotteryService impLotteryService;

    public AdminLotteryController(ImpLotteryService ImpLotteryService) {
        this.impLotteryService = ImpLotteryService;
    }

    @GetMapping("")
    public List<LotteryEntity> findAllLottery() {
        return impLotteryService.findAllLottery();
    }

    //    @GetMapping("/{id}")
//    public Optional<LotteryEntity> findLotteryById(@PathVariable("id") Long id) {
//        return ILotteryService.findLotteryById(id);
//    }

    @PostMapping("")
    public LotteryEntity addLottery(@RequestBody LotteryEntity lotteryEntity) {

        impLotteryService.saveLottery(lotteryEntity);
        return lotteryEntity;
    }


//    @PutMapping("")
//    public LotteryEntity updateLottery(@RequestBody LotteryEntity lotteryEntity) {
//        return ILotteryService.updateLottery(lotteryEntity);
//    }


    @DeleteMapping("/{id}")
    public void deleteLotteryById(@PathVariable("id") Long id) {
        impLotteryService.deleteLottery(id);
    }
}
