package com.kbtg.bootcamp.posttest.exception;

public class InternalServerStatusErrorException extends RuntimeException{
    public InternalServerStatusErrorException(String message){
        super(message);
    }
}
