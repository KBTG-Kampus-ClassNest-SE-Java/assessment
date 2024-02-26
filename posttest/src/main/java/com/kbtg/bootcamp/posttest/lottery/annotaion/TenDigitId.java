package com.kbtg.bootcamp.posttest.lottery.annotaion;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TenDigitIdValidator.class)
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TenDigitId {
    String message() default "must be a 10-digit number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
