package com.kbtg.bootcamp.posttest.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/users")
public class UserController {
  
  public UserController() {
  }

  @GetMapping("")
  public void getUsers() {
    // test
  }

  @GetMapping("/{userId}/lotteries")
  public String getAllLotteriesForUserId(@RequestParam String param) {
      return new String();
  }
  

  @PostMapping("/{userId}/lotteries/{ticketId}")
  public String buyLotteryForUserId(@RequestBody String entity) {
      //TODO: process POST request
      
      return entity;
  }
  
  @DeleteMapping("/{userId}/lotteries/{ticketId}")
  public String sellLotteryForUserId(@RequestParam String param) {
      return new String();
  }
}
