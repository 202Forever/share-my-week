package com.team202forever.sharemyweek.exception.messages;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GenericErrorMessage {

    private String message;

    private HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    public GenericErrorMessage(String message) {
        this.message = message;
    }

    public GenericErrorMessage(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getStatus() {
        return statusCode.value();
    }
}
