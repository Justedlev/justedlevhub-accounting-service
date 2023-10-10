package com.justedlev.hub.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "configuration")
public class ApplicationProperties {
    private Duration activityTime;
    private Duration offlineAfterTime;
    private Service service;

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "configuration.service")
    public static class Service {
        private String name;
        private String email;
        private String url;
    }
}
