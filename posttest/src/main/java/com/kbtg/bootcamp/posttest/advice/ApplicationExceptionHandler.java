package com.kbtg.bootcamp.posttest.advice;

import com.kbtg.bootcamp.posttest.exception.ElementNotFoundException;
import com.kbtg.bootcamp.posttest.exception.IllegalOperationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidRequestBodyHandler(MethodArgumentNotValidException e) {
        Map<String,String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> errorMap.put(error.getField(),error.getDefaultMessage())
        );

        Map<String,Object> response = new HashMap<>();
        response.put("errors",errorMap);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> invalidPathVariableHandler(RuntimeException e) {
        Map<String,String> response = new HashMap<>();

        // Format of error from path variable can be improved
        response.put("error",e.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IllegalOperationException.class
    })
    public ResponseEntity<?> IllegalOperationExceptionHandler(RuntimeException e) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("msg",e.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ElementNotFoundException.class
    })
    public ResponseEntity<?> ElementNotFoundExceptionHandler(RuntimeException e) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("msg",e.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }


}
