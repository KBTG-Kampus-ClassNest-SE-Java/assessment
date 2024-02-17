package com.kbtg.bootcamp.posttest.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @GetMapping
  public String findAllUsers() {
    return "Hello World";
  }

}
