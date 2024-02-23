package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

  private final LotteryService lotteryService;

  @Autowired
  public LotteryController(LotteryService lotteryService) {
    this.lotteryService = lotteryService;
  }

  @GetMapping
  public ResponseEntity<Object> getAllTickets() {
    try {
      return ResponseHandler.generateResponse(
          "get all tickets success",
          HttpStatus.OK,
          lotteryService.getAllTickets()
      );
    } catch (Exception e) {
      return ResponseHandler.generateResponse(
          e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR,
          null
      );
    }
  }
}
