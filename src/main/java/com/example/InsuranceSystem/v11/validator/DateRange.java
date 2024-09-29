package com.example.InsuranceSystem.v11.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {
    String message() default "Date of birth must be between {min} and {max} years old";
    
    int min() default 18;
    
    int max() default 100;
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
