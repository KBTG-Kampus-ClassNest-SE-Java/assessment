package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiExceptionResponse(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
}
