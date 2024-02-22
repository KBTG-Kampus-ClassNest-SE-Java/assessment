package com.kbtg.bootcamp.posttest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/lotteries")
    public Object getLotteryUser(){
        return Object;
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public Object postLotteryUser(@PathVariable int userId, @PathVariable String ticketId){
        return Object;
    }
}
