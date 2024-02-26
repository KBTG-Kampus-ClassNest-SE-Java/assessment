package com.kbtg.bootcamp.posttest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LotteryNotFoundException extends RuntimeException {
    public LotteryNotFoundException(String message) {
        super(message);
    }
}
