package com.kbtg.bootcamp.posttest.exceptions;

public class LotterySoldOutException extends RuntimeException{

    public LotterySoldOutException(String message) {
        super(message);
    }
}
