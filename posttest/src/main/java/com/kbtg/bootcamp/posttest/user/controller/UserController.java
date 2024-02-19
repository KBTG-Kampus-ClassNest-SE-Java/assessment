package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final LotteryService lotteryService;

  public UserController(LotteryService lotteryService) {
    this.lotteryService = lotteryService;
  }
  @PostMapping("/{userId}/lotteries/{ticketId}")
  public ResponseEntity<Object> buyLottery(@PathVariable int ticketId, @PathVariable int userId) {
      return ResponseHandler.generateResponse(
          "Buy lottery success.",
          HttpStatus.OK,
          lotteryService.buyLottery(ticketId, userId));
  }
  @GetMapping("/{userId}/lotteries/")
  public ResponseEntity<Object> getLotteryByUserId(@PathVariable int userId) {
      return ResponseHandler.generateResponse(
          "Get lotteries success.",
          HttpStatus.OK,
          lotteryService.getLotteriesByUserId(userId));
  }
  @DeleteMapping("/{userId}/lotteries/{ticketId}")
  public ResponseEntity<Object> deleteLottery(@PathVariable int ticketId, @PathVariable int userId) {
      return ResponseHandler.generateResponse(
          "sell lottery success.",
          HttpStatus.OK,
          lotteryService.sellLottery(ticketId, userId));
  }
}
