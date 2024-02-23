package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import jdk.jfr.Description;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lotteries")
public class AdminLotteryController {

    private final ImpLotteryService impLotteryService;

    public AdminLotteryController(ImpLotteryService impLotteryService) {
        this.impLotteryService = impLotteryService;
    }

    @Description("USE BY ADMIN FOR LIST ALL LOTTERY WHETHER SOLD OR NOT SOLD YET")
    @GetMapping("")
    public List<LotteryEntity> findAllLottery() {
        return impLotteryService.getAllLottery();
    }


    @Description("USE BY ADMIN FOR ADDING LOTTERY TO THE STORE")
    @PostMapping("")
    public LotteryEntity addLotteryToStore(@RequestBody LotteryEntity lotteryEntity) {
        impLotteryService.addLotteryToStore(lotteryEntity);
        return lotteryEntity;
    }

}
