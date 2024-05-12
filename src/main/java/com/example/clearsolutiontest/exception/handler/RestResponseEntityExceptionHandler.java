package com.example.clearsolutiontest.exception.handler;

import com.example.clearsolutiontest.exception.ApiRequestException;
import com.example.clearsolutiontest.exception.EmailException;
import com.example.clearsolutiontest.exception.InputFieldException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    protected ResponseEntity<Object> handleApiRequestException(ApiRequestException ex, WebRequest request) {
        return handleExceptionInternal(ex, createErrorResponse(
                ex.getStatus(),
                ex.getMessage(),
                request.getDescription(false)
                ),
                new HttpHeaders(),
                ex.getStatus(),
                request);
    }

    @ExceptionHandler(EmailException.class)
    protected ResponseEntity<Object> handleEmailException(EmailException ex, WebRequest request) {
        return handleExceptionInternal(ex, createErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getEmailError(),
                request.getDescription(false)
                ),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(InputFieldException.class)
    protected ResponseEntity<Object> handleInputFieldException(InputFieldException ex, WebRequest request) {
        return handleExceptionInternal(ex, createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                ex.getErrorsMap()
                ),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request.getDescription(false)
                ),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    private ErrorResponse createErrorResponse(HttpStatus status, String message, Object body) {
        return new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, body);
    }

    @Data
    @AllArgsConstructor
    static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private Object body;

    }
}
