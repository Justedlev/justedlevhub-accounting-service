package com.justedlev.hub.configuration.properties;

import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.Map;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "controller.account")
public class AccountControllerProperties {
    private String path;
    private Map<String, String> resources;

    @UtilityClass
    public static class Keys {
        public static final String ID = "id";
    }
}
