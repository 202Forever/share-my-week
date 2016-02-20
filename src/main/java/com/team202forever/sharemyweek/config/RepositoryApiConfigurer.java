package com.team202forever.sharemyweek.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;

@Configuration
public class RepositoryApiConfigurer implements RepositoryRestConfigurer {

    @Autowired
    private Validator validator;

    @Bean
    public Validator getLocaleValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

    }

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {

    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    @Override
    public void configureExceptionHandlerExceptionResolver(ExceptionHandlerExceptionResolver exceptionResolver) {

    }

    @Override
    public void configureHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {

    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {

    }
}
