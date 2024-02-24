package com.kbtg.bootcamp.posttest.validator.user_id;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsUserIdValidator implements ConstraintValidator<IsUserId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("\\d{1,10}") && Long.parseLong(value) > 0;
    }
}