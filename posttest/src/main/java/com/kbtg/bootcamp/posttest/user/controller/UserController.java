package com.kbtg.bootcamp.posttest.user.controller;

import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import com.kbtg.bootcamp.posttest.user.response.BuyTicketResponse;
import com.kbtg.bootcamp.posttest.user.response.RefundTicketResponse;
import com.kbtg.bootcamp.posttest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final LotteryService lotteryService;

  @Autowired
  public UserController(UserService userService, LotteryService lotteryService) {
    this.userService = userService;
    this.lotteryService = lotteryService;
  }

  @PostMapping("/{userId}/lotteries/{ticket}")
  public ResponseEntity<Object> buyTicketByUserId(
      @PathVariable Long userId,
      @PathVariable String ticket
  ) {
    try {
      if (lotteryService.checkAmountLottery(ticket)) {
        Long userTicketId = userService.buyTicketByUserId(userId, ticket);
        return ResponseHandler.generateResponse("buy ticket success", HttpStatus.OK,
            new BuyTicketResponse(userTicketId));
      } else {
        return ResponseHandler.generateResponse("ticket " + ticket + " is out of stock",
            HttpStatus.BAD_REQUEST,
            null);
      }
    } catch (NotFoundException e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,
          null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
          null);
    }
  }

  @GetMapping("/{userId}/lotteries")
  public ResponseEntity<Object> getTicketByUser(@PathVariable Long userId) {
    try {
      return ResponseHandler.generateResponse("get my tickets success", HttpStatus.OK,
          userService.getAllTicketsByUserId(userId));
    } catch (NotFoundException e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,
          null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
          null);
    }
  }

  @DeleteMapping("/{userId}/lotteries/{ticket}")
  public ResponseEntity<Object> createLottery(
      @PathVariable Long userId,
      @PathVariable String ticket
  ) {
    try {
      String result = userService.deleteUserTicket(userId, ticket);
      return ResponseHandler.generateResponse(
          "sell lottery ticket " + ticket + " success",
          HttpStatus.OK,
          new RefundTicketResponse(result));
    } catch (NotFoundException e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND,
          null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
          null);
    }
  }
}
