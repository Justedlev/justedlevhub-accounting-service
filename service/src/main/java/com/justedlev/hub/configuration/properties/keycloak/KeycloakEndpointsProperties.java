package com.justedlev.hub.configuration.properties.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak.endpoints")
public class KeycloakEndpointsProperties {
    private URI introspection;
    private URI token;
}
