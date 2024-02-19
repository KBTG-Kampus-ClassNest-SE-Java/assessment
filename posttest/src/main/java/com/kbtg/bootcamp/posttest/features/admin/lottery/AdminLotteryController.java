package com.kbtg.bootcamp.posttest.features.admin.lottery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/lotteries")
public class AdminLotteryController {

    @GetMapping("")
    private String greeting() {
        return "Greeting admin";
    }

}
