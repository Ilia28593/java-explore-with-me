package ru.practicum.main.exception;

import io.micrometer.core.lang.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.practicum.main.constant.Constants.DATE_FORMAT;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiError> handle(Exception ex) throws IOException {
        ApiError apiError = getApiError(Collections.singletonList(error(ex)), "Incorrectly made request.", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    public @NotNull
    ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                        @NonNull HttpHeaders headers,
                                                        @NonNull HttpStatus status,
                                                        @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField());
        }
        ApiError apiError = getApiError(errors, "The required object was not found.", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        ApiError apiError = getApiError(errors, "The required object was not found.", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    private String error(Exception e) throws IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace();
        String error = sw.toString();
        sw.close();
        pw.close();
        return error;
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)   // для всех ситуаций, если искомый объект не найден
    public ApiError handle(final NotFoundException e) throws IOException {
        return getApiError(error(e), HttpStatus.NOT_FOUND, "The required object was not found.", e.getLocalizedMessage());
    }

    @ExceptionHandler(ValidationCategoryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)   // Категория существует
    public ApiError handle(final ValidationCategoryException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "For the requested operation the conditions are not met.", e.getLocalizedMessage());
    }

    @ExceptionHandler(DuplicateNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)  //если есть дубликат Name.
    public ApiError handleThrowable(final DuplicateNameException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "The name is exists", e.getLocalizedMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)  //если есть дубликат Email.
    public ApiError handleThrowable(final DuplicateEmailException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "The email is exists, dublicate!", e.getLocalizedMessage());
    }

    @ExceptionHandler(EventDateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final EventDateException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "Incorrectly  time", e.getLocalizedMessage());
    }

    @ExceptionHandler(StateArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final StateArgumentException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, " The required object was not found.", e.getLocalizedMessage());
    }

    @ExceptionHandler(OverflowLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final OverflowLimitException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "The participant limit has been reached", e.getLocalizedMessage());
    }

    @ExceptionHandler(StatusPerticipationRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final StatusPerticipationRequestException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "The status Request NOT PENDING", e.getLocalizedMessage());
    }

    @ExceptionHandler(DuplicateParticipationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowable(final DuplicateParticipationException e) throws IOException {
        return getApiError(error(e), HttpStatus.CONFLICT, "Request has been", e.getLocalizedMessage());
    }

    private static ApiError getApiError(List<String> errors, String reason, String ex) {
        return new ApiError()
                .setErrors(errors)
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason(reason)
                .setMessage(ex)
                .setTimestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }

    private ApiError getApiError(String e, HttpStatus conflict, String requestHasBeen, String e1) {
        return new ApiError()
                .setErrors(Collections.singletonList(e))
                .setStatus(conflict)
                .setReason(requestHasBeen)
                .setMessage(e1)
                .setTimestamp((LocalDateTime.now()).format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }
}