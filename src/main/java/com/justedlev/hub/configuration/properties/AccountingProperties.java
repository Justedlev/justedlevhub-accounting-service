package com.justedlev.hub.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.Duration;

@Getter
@Setter
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "accounting")
public class AccountingProperties {
    private Duration activityTime;
    private Duration offlineAfterTime;
    private Service service;

    @Getter
    @Setter
    @ConfigurationPropertiesScan
    @ConfigurationProperties(prefix = "accounting.service")
    public static class Service {
        private String name;
        private String email;
        private String label;
        private String host;
    }
}
