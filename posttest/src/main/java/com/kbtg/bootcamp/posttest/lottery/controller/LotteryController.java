package com.kbtg.bootcamp.posttest.lottery.controller;


import com.kbtg.bootcamp.posttest.lottery.LotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LotteryController {

    private final LotteryServiceImp lotteryServiceImp;
    public LotteryController(LotteryServiceImp lotteryServiceImp) {
        this.lotteryServiceImp = lotteryServiceImp;
    }

    @PostMapping("/admin/lotteries")
    public Object LottoryAdminPost(@RequestParam LotteryRequest request){
        return lotteryServiceImp.createLotteryAdmin(request);
    }

    @GetMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> listLotteries(){
        return new ResponseEntity<String>(lotteryServiceImp.listAllLotteries(), HttpStatus.OK);
    }
}
