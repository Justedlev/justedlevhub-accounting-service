package com.justedlev.account.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "justedlev-service.account.service")
public class ServiceProperties {
    private String name;
    private String email;
    private String host;
}
