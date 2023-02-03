package com.justedlev.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class JAccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JAccountServiceApplication.class, args);
    }
}
