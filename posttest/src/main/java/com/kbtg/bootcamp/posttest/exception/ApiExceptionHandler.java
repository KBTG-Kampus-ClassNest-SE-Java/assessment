package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    ApiErrorResponse errorResponse = new ApiErrorResponse(
        e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

}