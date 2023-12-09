package com.justedlev.hub.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@Setter
@ConfigurationProperties("application.security")
public class SecurityProperties {
    private Set<String> whiteList;
}
