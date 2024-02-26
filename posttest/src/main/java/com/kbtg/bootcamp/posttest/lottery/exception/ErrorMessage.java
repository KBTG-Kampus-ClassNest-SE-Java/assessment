package com.kbtg.bootcamp.posttest.lottery.exception;

import java.util.Date;

public record ErrorMessage(int statusCode, Date timestamp, String message, String description) {

}
