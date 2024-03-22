package com.kbtg.bootcamp.posttest.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


@Data
public final class ExceptionResponseAPI {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime dateTime;

    public ExceptionResponseAPI(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

}
