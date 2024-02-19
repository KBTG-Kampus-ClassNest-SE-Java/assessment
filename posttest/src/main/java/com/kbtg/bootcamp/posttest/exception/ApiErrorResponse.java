package com.kbtg.bootcamp.posttest.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter

public class ApiErrorResponse {
  private final String message;

  private final HttpStatus httpStatus;

  private final ZonedDateTime dateTime;

  public ApiErrorResponse(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.dateTime = dateTime;
  }

}