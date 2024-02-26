package com.kbtg.bootcamp.posttest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LotterySoldOutException extends LotteryNotFoundException {
    public LotterySoldOutException(String message) {
        super(message);
    }
}
