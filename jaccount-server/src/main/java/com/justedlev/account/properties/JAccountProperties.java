package com.justedlev.account.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.Duration;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "jaccount")
public class JAccountProperties {
    private Duration activityTime;
    private Duration offlineAfterTime;
}
