package com.kbtg.bootcamp.posttest.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException){

        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                badRequestException.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                notFoundException.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }
}
