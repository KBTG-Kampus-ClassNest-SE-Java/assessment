package com.kbtg.bootcamp.posttest.userticket.exception;

public class InvalidUserTicketExceptionHandling extends  RuntimeException{
    public InvalidUserTicketExceptionHandling(String message)  {
        super(message);
    }
}