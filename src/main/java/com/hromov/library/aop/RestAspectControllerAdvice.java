package com.hromov.library.aop;

import com.hromov.library.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAspectControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException exception) {
        return ResponseEntity.status(exception.getHttpStatusCode())
                .body(formBody(exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(formBody(exception.getMessage()));
    }

    private String formBody(String message) {
        return String.format("{\"message\":\"%s\"", message);
    }
}
