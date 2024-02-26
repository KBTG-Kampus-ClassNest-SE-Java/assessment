package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ AlreadyExistedException.class })
    public ResponseEntity<Object> handleAlreadyExistedException(AlreadyExistedException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getMessage(), HttpStatus.CONFLICT, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({ HandlerMethodValidationException.class })
    public ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                getAllValidationExceptions(e), HttpStatus.BAD_REQUEST, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    private String getAllValidationExceptions(HandlerMethodValidationException e){
        return e.getAllErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.joining(", "));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                getAllValidationExceptions(e), HttpStatus.BAD_REQUEST, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getAllValidationExceptions(MethodArgumentNotValidException e){
        return e.getAllErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.joining(", "));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
