package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiErrorResponse {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime dateTime;

    public ApiErrorResponse(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
}
