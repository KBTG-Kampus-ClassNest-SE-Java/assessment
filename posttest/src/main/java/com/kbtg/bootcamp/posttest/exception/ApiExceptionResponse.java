package com.kbtg.bootcamp.posttest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiExceptionResponse {
    @Getter
    private final String message;

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final ZonedDateTime dateTime;

    public ApiExceptionResponse(
            String message,
            HttpStatus httpStatus,
            ZonedDateTime dateTime
    ) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

}
