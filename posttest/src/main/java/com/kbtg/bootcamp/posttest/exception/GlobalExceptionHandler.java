package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateLotteryException.class)
    public ResponseEntity<String> handleDuplicateLotteryException(DuplicateLotteryException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotExistUserIdException.class)
    public ResponseEntity<String> handleNotExistUserIdException(NotExistUserIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotExistLotteryException.class)
    public ResponseEntity<String> handleNotExistLotteryNumberException(NotExistLotteryException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(LotteryNotBelongToUserException.class)
    public ResponseEntity<String> handleLotteryNotBelongToUserException(LotteryNotBelongToUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
