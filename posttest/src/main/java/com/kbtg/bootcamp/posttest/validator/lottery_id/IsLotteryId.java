package com.kbtg.bootcamp.posttest.validator.lottery_id;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsLotteryIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLotteryId {
    String message() default "Invalid lottery id must be exactly 6 characters long and contain only numeric characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}