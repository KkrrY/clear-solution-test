package com.example.clearsolutiontest.annotation;

import com.example.clearsolutiontest.annotation.validator.AgeGreaterThanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import static com.example.clearsolutiontest.constants.ErrorMessage.INCORRECT_AGE;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AgeGreaterThanValidator.class})
public @interface AgeGreaterThan {
    String message() default INCORRECT_AGE;
    String valueFromProperties() default "age.min"; // Default property name
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
