package com.kbtg.bootcamp.posttest.admin.controller;

import com.kbtg.bootcamp.posttest.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.response.CreateLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final LotteryService lotteryService;

  @Autowired
  public AdminController(LotteryService lotteryService) {
    this.lotteryService = lotteryService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/lotteries")
  public ResponseEntity<Object> createLottery(
      @Valid @RequestBody CreateLotteryRequest createLotteryRequest
  ) {
    try {
      if (!lotteryService.checkExistTicket(createLotteryRequest.getTicket())) {
        String ticket = lotteryService.createLottery(createLotteryRequest);
        return ResponseHandler.generateResponse(
            "create lottery Success",
            HttpStatus.CREATED,
            new CreateLotteryResponse(ticket));
      } else {
        return ResponseHandler.generateResponse(
            "ticket" + createLotteryRequest.getTicket() + " already exists",
            HttpStatus.CONFLICT,
            null);
      }
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getLocalizedMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR,
          null);
    }
  }
}
