package com.justedlev.hub.configuration;

import com.justedlev.hub.api.JStorageFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = JStorageFeignClient.class)
public class FeignClientsConfiguration {
}
