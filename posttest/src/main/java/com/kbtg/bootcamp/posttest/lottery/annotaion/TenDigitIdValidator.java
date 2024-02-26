package com.kbtg.bootcamp.posttest.lottery.annotaion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TenDigitIdValidator implements ConstraintValidator<TenDigitId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("\\d{10}");
    }
}
