package com.kbtg.bootcamp.posttest.authentication.request;


public record AuthenticationRequest (
  String username,
  String password
){}