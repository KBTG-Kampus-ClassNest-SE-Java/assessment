package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerAPI extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException) {

        ExceptionResponseAPI exceptionResponseAPI = new ExceptionResponseAPI(
                badRequestException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseAPI, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InternalServerStatusErrorException.class})
    public ResponseEntity<Object> handleInternalServiceException(InternalServerStatusErrorException statusInternalServerErrorException) {

        ExceptionResponseAPI exceptionResponseAPI= new ExceptionResponseAPI(
                statusInternalServerErrorException.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseAPI, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
