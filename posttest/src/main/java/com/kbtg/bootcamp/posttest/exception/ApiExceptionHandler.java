package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ BadRequestException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException error) {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(error.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(MethodArgumentNotValidException error) {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(error.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ApiErrorResponse> handleValidationRequestException(NotFoundException error) {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
