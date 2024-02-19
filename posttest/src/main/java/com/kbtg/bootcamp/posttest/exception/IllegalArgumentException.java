package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class IllegalArgumentException extends RuntimeException{
  public IllegalArgumentException(String message) {
    super(message);
  }

}
