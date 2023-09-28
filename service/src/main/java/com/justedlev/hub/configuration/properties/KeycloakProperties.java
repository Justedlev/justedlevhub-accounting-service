package com.justedlev.hub.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Setter(AccessLevel.PACKAGE)
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private URI issuerUri;
    private URI jwkSetUri;
}
