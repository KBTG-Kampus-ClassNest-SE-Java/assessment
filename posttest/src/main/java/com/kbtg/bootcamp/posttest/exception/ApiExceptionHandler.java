package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException e) {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(BadRequestException e) {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(e.getMessage(), HttpStatus.OK, ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        List<Map<String, String>> errorMessages = e.getErrors().entrySet().stream()
                .map(entry -> Collections.singletonMap(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", errorMessages);
        responseBody.put("httpStatus", HttpStatus.BAD_REQUEST);
        responseBody.put("dateTime", ZonedDateTime.now());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
