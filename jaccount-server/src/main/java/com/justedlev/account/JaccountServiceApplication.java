package com.justedlev.account;

import com.justedlev.account.properties.JaccountProperties;
import com.justedlev.account.properties.ServiceProperties;
import com.justedlev.storage.client.JstorageFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients(clients = JstorageFeignClient.class)
@EnableConfigurationProperties({
        JaccountProperties.class,
        ServiceProperties.class,
})
public class JaccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JaccountServiceApplication.class, args);
    }
}
