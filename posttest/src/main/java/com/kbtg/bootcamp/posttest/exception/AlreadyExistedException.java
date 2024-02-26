package com.kbtg.bootcamp.posttest.exception;


public class AlreadyExistedException extends RuntimeException {
    public AlreadyExistedException(String message) {
        super(message);
    }
}
