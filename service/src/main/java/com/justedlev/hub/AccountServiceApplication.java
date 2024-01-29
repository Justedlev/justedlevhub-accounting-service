package com.justedlev.hub;

import com.justedlev.hub.configuration.properties.AccountControllerProperties;
import com.justedlev.hub.configuration.properties.ApplicationProperties;
import com.justedlev.hub.configuration.properties.CloudAmqpProperties;
import com.justedlev.hub.configuration.properties.JNotificationProperties;
import com.justedlev.hub.configuration.properties.keycloak.KeycloakClientProperties;
import com.justedlev.hub.configuration.properties.keycloak.KeycloakJwtConverterProperties;
import com.justedlev.hub.configuration.properties.keycloak.KeycloakProperties;
import com.justedlev.hub.configuration.properties.keycloak.KeycloakUserProperties;
import com.justedlev.hub.configuration.security.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableEnversRepositories
@EnableConfigurationProperties({
        ApplicationProperties.class,
        ApplicationProperties.Service.class,
        JNotificationProperties.class,
        JNotificationProperties.Service.class,
        CloudAmqpProperties.class,
        CloudAmqpProperties.Queue.class,
        KeycloakProperties.class,
        KeycloakClientProperties.class,
        KeycloakJwtConverterProperties.class,
        KeycloakUserProperties.class,
        SecurityProperties.class,
        AccountControllerProperties.class,
})
public class AccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
