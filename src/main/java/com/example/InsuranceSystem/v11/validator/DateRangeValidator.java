package com.example.InsuranceSystem.v11.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class DateRangeValidator implements ConstraintValidator<DateRange, LocalDate> {
    
    private int min;
    private int max;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) {
            return true; // Not null is handled by @NotNull
        }

        int age = Period.between(dob, LocalDate.now()).getYears();
        return age >= min && age <= max;
    }
}
