package com.kbtg.bootcamp.posttest.lottery;

public class NotExistUserIdException extends RuntimeException {
    public NotExistUserIdException(String message) {
        super(message);
    }
}
