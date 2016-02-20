package com.team202forever.sharemyweek.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class SpringDataRestApiExceptionHandler extends RepositoryRestExceptionHandler {

    /**
     * Creates a new {@link SpringDataRestApiExceptionHandler} using the given {@link MessageSource}.
     *
     * @param messageSource must not be {@literal null}.
     */
    @Autowired
    public SpringDataRestApiExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }
}
