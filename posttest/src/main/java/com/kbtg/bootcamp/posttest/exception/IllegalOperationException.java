package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;

public class IllegalOperationException extends RuntimeException{
    public IllegalOperationException(String msg) { super(msg); }
}
