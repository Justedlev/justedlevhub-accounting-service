package com.justedlevhub.account.configuration;

import com.justedlevhub.account.api.JStorageFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = JStorageFeignClient.class)
public class FeignClientsConfiguration {
}
