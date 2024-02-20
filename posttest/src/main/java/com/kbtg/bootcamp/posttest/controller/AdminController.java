package com.kbtg.bootcamp.posttest.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/lotteries")
    public Object postLotteryAdmin(@RequestParam LotteryPostRecord request){

        return Object;
    }



    record LotteryPostRecord(){}


}
