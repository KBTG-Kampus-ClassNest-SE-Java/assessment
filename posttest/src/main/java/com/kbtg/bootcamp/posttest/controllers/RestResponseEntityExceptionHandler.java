package com.kbtg.bootcamp.posttest.controllers;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<List<Map<String, Object>>> handleValidationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        List<Map<String, Object>> errors = violations.stream().map((violation) -> {
            Map<String, Object> field = new HashMap<>();
            field.put("rejectedValue", violation.getInvalidValue());
            field.put("message", violation.getConstraintDescriptor().getAttributes().get("message"));
            return field;
        }).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List<Map<String, Object>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((fieldError) -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("rejectedValue", fieldError.getRejectedValue());
                    error.put("message", fieldError.getDefaultMessage());
                    return error;
                })
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }
}
