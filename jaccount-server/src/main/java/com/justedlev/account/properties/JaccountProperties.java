package com.justedlev.account.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.Duration;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "jaccount")
public class JaccountProperties {
    private Duration deactivationTime;
    private Integer codeLength;
    private String clientId;
    private String clientSecret;
    private Duration activityTime;
    private Duration offlineAfterTime;
}
