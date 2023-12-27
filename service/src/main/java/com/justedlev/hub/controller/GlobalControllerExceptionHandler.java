package com.justedlev.hub.controller;

import com.justedlev.hub.model.response.ErrorDetailsResponse;
import com.justedlev.hub.model.response.ValidationErrorResponse;
import com.justedlev.hub.model.response.ViolationResponse;
import feign.FeignException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            EntityExistsException.class,
            IllegalStateException.class,
    })
    public ResponseEntity<ErrorDetailsResponse> handleConflictExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        var errorDetails = buildDetailsResponse(ex, request);

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                              WebRequest request) {
        log.error(ex.getMessage(), ex);
        var errorDetails = buildDetailsResponse(ex, request);

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ErrorDetailsResponse> handleFeignException(FeignException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        var errorDetails = buildDetailsResponse(ex, request);
        var status = ex.status() > 0 ? HttpStatus.valueOf(ex.status()) : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(errorDetails, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
                                                                                      WebRequest request) {
        log.error(ex.getMessage(), ex);
        var violations = ex.getConstraintViolations()
                .stream()
                .map(current -> ViolationResponse.builder()
                        .fieldName(current.getPropertyPath().toString())
                        .message(current.getMessage())
                        .build())
                .toList();
        var response = ValidationErrorResponse.builder()
                .details(request.getDescription(false))
                .violations(violations)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        log.error(ex.getMessage(), ex);
        super.handleMethodArgumentNotValid(ex, headers, status, request);
        var violations = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(current -> ViolationResponse.builder()
                        .fieldName(current.getField())
                        .message(current.getDefaultMessage())
                        .build())
                .toList();
        var error = ValidationErrorResponse.builder()
                .details(request.getDescription(false))
                .violations(violations)
                .build();

        return new ResponseEntity<>(error, status);
    }

    private ErrorDetailsResponse buildDetailsResponse(Throwable throwable, WebRequest webRequest) {
        return ErrorDetailsResponse.builder()
                .details(webRequest.getDescription(false))
                .message(throwable.getLocalizedMessage())
                .build();
    }
}
