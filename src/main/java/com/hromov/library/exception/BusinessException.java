package com.hromov.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;

    public BusinessException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
