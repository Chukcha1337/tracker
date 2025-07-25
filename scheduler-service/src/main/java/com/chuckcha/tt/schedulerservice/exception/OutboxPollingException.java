package com.chuckcha.tt.schedulerservice.exception;

public class OutboxPollingException extends RuntimeException {
    public OutboxPollingException(String message, Throwable cause) {
        super(message, cause);
    }
}
