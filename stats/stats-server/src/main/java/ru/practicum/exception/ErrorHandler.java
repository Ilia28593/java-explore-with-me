package ru.practicum.exception;

import io.micrometer.core.lang.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.practicum.constant.Constants.DATE_FORMAT;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiError> handle(Exception ex) {
        ApiError apiError = new ApiError()
                .setErrors(Collections.singletonList(ex.getLocalizedMessage()))
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("Incorrectly made request.")
                .setMessage(ex.getLocalizedMessage())
                .setTimestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    public @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                        @NonNull HttpHeaders headers,
                                                        @NonNull HttpStatus status,
                                                        @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField());
        }
        ApiError apiError = new ApiError()
                .setErrors(errors)
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("The required object was not found.")
                .setMessage(ex.getLocalizedMessage())
                .setTimestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern(DATE_FORMAT)));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        ApiError apiError = new ApiError()
                .setErrors(errors)
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("The required object was not found.")
                .setMessage(ex.getLocalizedMessage())
                .setTimestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern(DATE_FORMAT)));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}