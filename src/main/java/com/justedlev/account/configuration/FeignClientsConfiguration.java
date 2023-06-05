package com.justedlev.account.configuration;

import com.justedlev.storage.client.JStorageFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = JStorageFeignClient.class)
public class FeignClientsConfiguration {
}
