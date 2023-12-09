package com.justedlev.hub.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakLogoutHandler implements LogoutHandler {
    private final RestTemplate restTemplate;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutFromKeycloak((OidcUser) authentication.getPrincipal());
    }

    private void logoutFromKeycloak(OidcUser user) {
        var endSessionEndpoint = buildEndSessionEndpoint(user);
        var logoutResponse = restTemplate.getForEntity(endSessionEndpoint, String.class);

        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            log.info("Successfully logged out from Keycloak");
        } else {
            log.error("Could not propagate logout to Keycloak");
        }
    }

    private String buildEndSessionEndpoint(OidcUser user) {
        return UriComponentsBuilder
                .fromUriString(user.getIssuer().toString())
                .path("/protocol/openid-connect/logout")
                .queryParam("id_token_hint", user.getIdToken().getTokenValue())
                .toUriString();
    }
}
