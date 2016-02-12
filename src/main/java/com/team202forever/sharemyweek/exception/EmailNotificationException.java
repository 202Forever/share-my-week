package com.team202forever.sharemyweek.exception;

public class EmailNotificationException extends RuntimeException {

    public EmailNotificationException(String message) {
        super(message);
    }

    public EmailNotificationException(String message, Throwable exception) {
        super(message, exception);
    }
}
