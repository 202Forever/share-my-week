package com.team202forever.sharemyweek.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.team202forever.sharemyweek.exception.messages.GenericErrorMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.WebApplicationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

@ControllerAdvice
public class ExceptionHandlers {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(EmailNotificationException.class)
    ResponseEntity<GenericErrorMessage> handle(EmailNotificationException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(new GenericErrorMessage("Please verify your information is correct. If you believe your information is correct, please contant us at support@sharemyweek.com"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(WebApplicationException.class)
    ResponseEntity<GenericErrorMessage> handle(WebApplicationException e) {
        logger.error(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.valueOf(e.getResponse().getStatus());
        return new ResponseEntity<>(new GenericErrorMessage(e.getMessage(), httpStatus), new HttpHeaders(), httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<GenericErrorMessage> handle(HttpMessageNotReadableException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(new GenericErrorMessage("Your request is invalid", HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    ResponseEntity<String> handle(HttpClientErrorException e) {
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getResponseBodyAsString(), new HttpHeaders(), e.getStatusCode());
    }

    @ExceptionHandler({
        UndeclaredThrowableException.class,
        InvocationTargetException.class,
        IllegalArgumentException.class,
        ClassCastException.class,
        ConversionFailedException.class,
        NullPointerException.class
    })
    ResponseEntity<GenericErrorMessage> handle(Exception e) {
        logger.error("Unhandled excpetion for an request", e);
        return new ResponseEntity<>(new GenericErrorMessage("Sorry, we are unable to process your request at this moment."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
