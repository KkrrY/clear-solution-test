package com.example.clearsolutiontest.annotation.validator;

import com.example.clearsolutiontest.annotation.AgeGreaterThan;
import com.example.clearsolutiontest.annotation.PropertiesReader;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeGreaterThanValidator implements ConstraintValidator<AgeGreaterThan, LocalDate> {

    private int minAge;

    @Override
    public void initialize(AgeGreaterThan constraintAnnotation) {
        String propertyName = constraintAnnotation.valueFromProperties();
        minAge = Integer.parseInt(PropertiesReader.getProperty(propertyName));
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null check
        }

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(value, currentDate);
        int age = period.getYears();

        return age >= minAge;
    }
}
