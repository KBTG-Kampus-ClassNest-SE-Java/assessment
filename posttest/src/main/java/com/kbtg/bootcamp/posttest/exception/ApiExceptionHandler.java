package com.kbtg.bootcamp.posttest.exception;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFound(NotFoundException notFoundException) {
    ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
        notFoundException.getMessage(),
        HttpStatus.NOT_FOUND,
        ZonedDateTime.now()
    );

    return new ResponseEntity<>(apiExceptionResponse,HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {BadRequestException.class})
  public ResponseEntity<Object> handleBadRequest(BadRequestException badRequestException) {
    ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
        badRequestException.getMessage(),
        HttpStatus.BAD_REQUEST,
        ZonedDateTime.now()
    );

    return new ResponseEntity<>(apiExceptionResponse,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {InternalServerException.class})
  public ResponseEntity<Object> handleInternalServer(InternalServerException internalServerException) {
    ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
        internalServerException.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR,
        ZonedDateTime.now()
    );

    return new ResponseEntity<>(apiExceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
  }


  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
    ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
        illegalArgumentException.getMessage(),
        HttpStatus.BAD_REQUEST,
        ZonedDateTime.now()
    );

    return new ResponseEntity<>(apiExceptionResponse,HttpStatus.BAD_REQUEST);
  }




}
