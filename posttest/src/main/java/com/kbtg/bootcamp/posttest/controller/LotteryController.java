package com.kbtg.bootcamp.posttest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {
  public LotteryController() {
  }

  @GetMapping("")
  public void getLotteries() {
    // test
  }
}
