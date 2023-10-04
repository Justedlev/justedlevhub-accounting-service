package com.justedlev.hub.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.Set;

@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private URI issuerUri;
    private URI jwkSetUri;
    private JwtConverterProperties jwtConverter;

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "keycloak.jwt-converter")
    public static class JwtConverterProperties {
        private Set<String> roleClaims;
        private String usernameAttribute;
    }
}
