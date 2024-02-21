package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.app.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }
  @PostMapping("/{userId}/lotteries/{ticketId}")
  public ResponseEntity<Object> buyLottery(@PathVariable String ticketId, @PathVariable String userId) {
      return ResponseHandler.generateResponse(
          "Buy lottery success.",
          HttpStatus.OK,
          userService.buyLottery(ticketId, userId));
  }
  @GetMapping("/{userId}/lotteries")
  public ResponseEntity<Object> getLotteryByUserId(@PathVariable String userId) {
      return ResponseHandler.generateResponse(
          "Get lotteries success.",
          HttpStatus.OK,
          userService.getLotteriesByUserId(userId));
  }
  @DeleteMapping("/{userId}/lotteries/{ticketId}")
  public ResponseEntity<Object> deleteLottery(@PathVariable String ticketId, @PathVariable String userId) {
      return ResponseHandler.generateResponse(
          "Sell lottery success.",
          HttpStatus.OK,
          userService.sellLottery(ticketId, userId));
  }
}
