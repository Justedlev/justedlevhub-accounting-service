package com.justedlev.hub;

import com.justedlev.hub.configuration.properties.CloudAmqpProperties;
import com.justedlev.hub.configuration.properties.JNotificationProperties;
import com.justedlev.hub.configuration.properties.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "requestContextAuditorAware")
@EnableEnversRepositories
@EnableConfigurationProperties({
        Properties.class,
        Properties.Service.class,
        JNotificationProperties.class,
        JNotificationProperties.Service.class,
        CloudAmqpProperties.class,
        CloudAmqpProperties.Queue.class,
})
public class AccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
