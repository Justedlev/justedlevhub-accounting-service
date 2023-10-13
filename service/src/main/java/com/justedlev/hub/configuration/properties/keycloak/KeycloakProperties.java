package com.justedlev.hub.configuration.properties.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private URI issuerUri;
    private URI jwkSetUri;
    private URI introspectionUri;
    private URI tokenUri;
    private KeycloakClientProperties client;
    private KeycloakJwtConverterProperties jwtConverter;
}
