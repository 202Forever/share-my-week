package com.team202forever.sharemyweek.exception.messages;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GenericErrorMessage {

    private String message;

    public GenericErrorMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
