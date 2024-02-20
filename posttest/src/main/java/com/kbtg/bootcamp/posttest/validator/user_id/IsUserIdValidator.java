package com.kbtg.bootcamp.posttest.validator.user_id;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsUserIdValidator implements ConstraintValidator<IsUserId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !(value.length() >= 1 && value.length() <= 10)) {
            return false;
        }

        try {
            long number = Long.parseLong(value);

            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}