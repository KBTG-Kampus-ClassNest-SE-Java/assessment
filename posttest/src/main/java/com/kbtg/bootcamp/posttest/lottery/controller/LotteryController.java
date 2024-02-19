package com.kbtg.bootcamp.posttest.lottery.controller;


import com.kbtg.bootcamp.posttest.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/lotteries")
@RestController
@Validated
public class LotteryController {

  private final LotteryService lotteryService;

  public LotteryController(LotteryService lotteryService) {
    this.lotteryService = lotteryService;
  }

  @GetMapping
  public ResponseEntity<Object> getLotteries() {
    try {
      return ResponseHandler.generateResponse(
          "Get lotteries success.",
          HttpStatus.OK,
          lotteryService.getLotteries());
    } catch (Exception e) {
      return ResponseHandler.generateResponse(
          e.getMessage(),
          HttpStatus.BAD_REQUEST,
          null);
    }
  }

}