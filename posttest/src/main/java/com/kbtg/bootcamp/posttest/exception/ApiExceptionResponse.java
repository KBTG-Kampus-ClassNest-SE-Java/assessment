package com.kbtg.bootcamp.posttest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ApiExceptionResponse {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime dateTime;

    public ApiExceptionResponse(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }
}

