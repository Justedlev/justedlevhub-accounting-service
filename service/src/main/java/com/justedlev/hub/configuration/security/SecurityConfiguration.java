package com.justedlev.hub.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration {
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",
            "/error"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
//                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2ResourceServerCustomizer -> oauth2ResourceServerCustomizer
                        .jwt(Customizer.withDefaults())
                )
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers("/api/v1/accounts/history/{id}").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/accounts/{id}").hasAuthority("user")
//                        .requestMatchers("/api/v1/accounts/{id}").hasAnyRole("user")
                                .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new KeycloakJwtAuthenticationConverter();
    }
}
