package com.team202forever.sharemyweek.data.thirdparty.eventbrite;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sharemyweek.thirdparty.eventbrite", ignoreUnknownFields = true)
@Getter
@Setter
public class EventbriteProperties {

    private String token;

    private String apiPath;
}
