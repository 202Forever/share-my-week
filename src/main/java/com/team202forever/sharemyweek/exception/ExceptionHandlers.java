package com.team202forever.sharemyweek.exception;

import com.team202forever.sharemyweek.exception.messages.GenericErrorMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(EmailNotificationException.class)
    ResponseEntity<GenericErrorMessage> handle(EmailNotificationException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(new GenericErrorMessage("Please verify your information is correct. If you believe your information is correct, please contant us at support@sharemyweek.com"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    ResponseEntity<GenericErrorMessage> handle(Exception e) {
        logger.error("Unhandled excpetion for an request", e);
        return new ResponseEntity<>(new GenericErrorMessage("Sorry, we are unable to process your request at this moment."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
