package com.kbtg.bootcamp.posttest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String error;
    private String message;
    private String path;
}