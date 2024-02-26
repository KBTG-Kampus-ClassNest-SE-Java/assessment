package com.kbtg.bootcamp.posttest.exception;

import java.util.Map;

public class ValidationException extends Exception {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super(errors.toString()); // For logging purpose
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
