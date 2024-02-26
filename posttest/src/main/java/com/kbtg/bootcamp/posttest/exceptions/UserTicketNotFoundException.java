package com.kbtg.bootcamp.posttest.exceptions;

public class UserTicketNotFoundException extends RuntimeException{
    public UserTicketNotFoundException(String message) {
        super(message);
    }
}
