package com.bookkeeping.exception;

import com.bookkeeping.DTO.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Log4j2
@ControllerAdvice
public class BookExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleBookKeepingValidationException(ConstraintViolationException exception) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("400")
                .timestamp(LocalDateTime.now())
                .build();

        ArrayList<ConstraintViolation<?>> constraintViolations = new ArrayList<>(exception.getConstraintViolations());
        if (!constraintViolations.isEmpty()) {
            errorResponse.setErrorMessage(constraintViolations.get(0).getMessageTemplate());
        }

        log.info("Exceptions : {}", constraintViolations);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException exception) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("404")
                .timestamp(LocalDateTime.now())
                .errorMessage(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers
            , HttpStatusCode status, WebRequest request) {

        log.error("Error : {}", exception);

        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst().orElse(exception.getMessage());

        log.error("Error : {}", errorMessage);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("400")
                .timestamp(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        //return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler(ClassificationValidationException.class)
    public ResponseEntity<ErrorResponse> handleClassificationValidationException(ClassificationValidationException exception) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("400")
                .timestamp(LocalDateTime.now())
                .errorMessage(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Message : {}",ex.getMessage());
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
