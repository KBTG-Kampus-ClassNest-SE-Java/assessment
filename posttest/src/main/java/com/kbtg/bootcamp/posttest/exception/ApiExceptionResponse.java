package com.kbtg.bootcamp.posttest.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
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
