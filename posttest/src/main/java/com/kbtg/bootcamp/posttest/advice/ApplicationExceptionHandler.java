package com.kbtg.bootcamp.posttest.advice;

import com.kbtg.bootcamp.posttest.exception.IllegalOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<?> IllegalOperationExceptionHandler(IllegalOperationException e) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("msg",e.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
