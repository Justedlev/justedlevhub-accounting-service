package com.justedlev.hub.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "jnotification")
public class JNotificationProperties {
    @Data
    @ConfigurationPropertiesScan
    @ConfigurationProperties(prefix = "jnotification.service")
    public static class Service {
        private String name;
        private String email;
    }
}
