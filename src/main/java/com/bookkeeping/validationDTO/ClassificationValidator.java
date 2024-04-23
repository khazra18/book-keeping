package com.bookkeeping.validationDTO;

import com.bookkeeping.Entity.Classification;
import com.bookkeeping.exception.ClassificationValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ClassificationValidator implements ConstraintValidator<ValidClassification, Classification> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValidClassification constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Classification classification, ConstraintValidatorContext constraintValidatorContext) {
        if (classification == null) {
            throw new ClassificationValidationException("Not a valid input");
        }
        if (!acceptedValues.contains(classification.toString()))
            throw new ClassificationValidationException("Please give a valid input, Input must of these " + classification);

        return acceptedValues.contains(classification.toString());
    }
}
