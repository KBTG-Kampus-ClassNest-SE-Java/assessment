package com.kbtg.bootcamp.posttest.lottery.exception;

public class LotteryUnavailableException  extends RuntimeException {
    public LotteryUnavailableException(String errorMessage) {
        super(errorMessage);
    }
}
