package com.kbtg.bootcamp.posttest.exception;

public class NotExistUserIdException extends RuntimeException {
    public NotExistUserIdException(String message) {
        super(message);
    }
}
