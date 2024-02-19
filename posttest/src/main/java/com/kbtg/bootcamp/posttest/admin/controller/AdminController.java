package com.kbtg.bootcamp.posttest.admin.controller;

import com.kbtg.bootcamp.posttest.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/lotteries")
public class AdminController {

  private final LotteryService lotteryService;

  public AdminController(LotteryService lotteryService) {
    this.lotteryService = lotteryService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Object> createLottery(@Valid @RequestBody CreateLotteryRequest createLotteryRequest) {
    try {
      return ResponseHandler.generateResponse(
          "Create lottery success.",
          HttpStatus.CREATED,
          lotteryService.createLottery(createLotteryRequest));
    } catch (Exception e) {
      return ResponseHandler.generateResponse(
          e.getMessage(),
          HttpStatus.BAD_REQUEST,
          null);
    }
  }




}
