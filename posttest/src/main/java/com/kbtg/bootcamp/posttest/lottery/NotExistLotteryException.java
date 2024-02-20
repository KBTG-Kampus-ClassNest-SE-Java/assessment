package com.kbtg.bootcamp.posttest.lottery;

public class NotExistLotteryException extends RuntimeException {
    public NotExistLotteryException(String message) {
        super(message);
    }
}
