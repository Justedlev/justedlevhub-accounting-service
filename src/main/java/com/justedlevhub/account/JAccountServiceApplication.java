package com.justedlevhub.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableDiscoveryClient
public class JAccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JAccountServiceApplication.class, args);
    }
}
