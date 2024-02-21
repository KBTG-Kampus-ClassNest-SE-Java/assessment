package com.kbtg.bootcamp.posttest.exeption;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ApiExceptionResponse {
    private final String message;
    private final HttpStatus httpStatus;

    public ApiExceptionResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
