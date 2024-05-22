package com.example.clearsolutiontest.annotation;

import com.example.clearsolutiontest.annotation.validator.AgeGreaterThanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AgeGreaterThanValidator.class})
public @interface AgeGreaterThan {
    String message() default "Must be at least {minAge} years old";
    String valueFromProperties() default "age.min"; // Default property name
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
