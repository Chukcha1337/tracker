package com.chuckcha.tt.authservice.handler;

import com.chuckcha.tt.core.exception.KeyLoadException;
import com.chuckcha.tt.core.exception.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handle(UserRegistrationException exp) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exp.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handle(AccessDeniedException exp) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exp.getMessage());
    }

    @ExceptionHandler(KeyLoadException.class)
    public ResponseEntity<String> handle(KeyLoadException exp) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(exp.getMessage());
    }


}
