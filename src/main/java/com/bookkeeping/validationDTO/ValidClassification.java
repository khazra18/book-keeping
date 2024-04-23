package com.bookkeeping.validationDTO;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClassificationValidator.class)
public @interface ValidClassification {

    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid classification";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
