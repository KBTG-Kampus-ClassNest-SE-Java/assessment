package com.kbtg.bootcamp.posttest.validator.lottery_id;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsLotteryIdValidator implements ConstraintValidator<IsLotteryId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("\\d{6}");
    }
}