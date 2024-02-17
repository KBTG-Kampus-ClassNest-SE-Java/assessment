package com.kbtg.bootcamp.posttest.authentication.dto;
import java.sql.Timestamp;


public class AuthResponse {

  private String accessToken;

  private final Timestamp tokenType = new Timestamp(System.currentTimeMillis());


  public AuthResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}