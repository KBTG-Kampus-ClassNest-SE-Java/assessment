package com.kbtg.bootcamp.posttest.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(MethodArgumentNotValidException notValidException, WebRequest request) {
        List<String> error = notValidException.getFieldErrors()
                .stream()
                .map(f -> f.getField() + " " + f.getDefaultMessage())
                .toList();

        return new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                null,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.join(", ", error),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(ConstraintViolationException constraintViolationException, WebRequest request) {
        List<String> error = constraintViolationException.getConstraintViolations()
                .stream()
                .map(f -> f.getPropertyPath().toString() + " " + f.getMessage())
                .toList();

        return new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                null,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.join(", ", error),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(BadRequestException notValidException, WebRequest request) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                null,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                notValidException.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleUnauthorizedException(UnauthorizedException unauthorizedException, WebRequest request) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                null,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                unauthorizedException.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundRequestException(NotFoundException notValidException, WebRequest request) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                null,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                notValidException.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {InternalServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleInternalServerException(InternalServerException internalServerException, WebRequest request) {
        return new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                internalServerException.getMessage(),
                request.getDescription(false)
        );
    }
}