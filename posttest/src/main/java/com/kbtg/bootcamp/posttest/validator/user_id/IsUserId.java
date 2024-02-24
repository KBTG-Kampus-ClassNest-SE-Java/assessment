package com.kbtg.bootcamp.posttest.validator.user_id;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsUserIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUserId {
    String message() default "Invalid user id must be exactly 1-10 characters long and contain only numeric characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}