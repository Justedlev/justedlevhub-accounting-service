package com.justedlev.account;

import com.justedlev.account.properties.JAccountProperties;
import com.justedlev.account.properties.ServiceProperties;
import com.justedlev.storage.client.JStorageFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients(clients = JStorageFeignClient.class)
@EnableConfigurationProperties({
        JAccountProperties.class,
        ServiceProperties.class,
})
public class JAccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JAccountServiceApplication.class, args);
    }
}