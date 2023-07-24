package com.justedlev.hub.configuration;

import com.justedlev.hub.properties.JStorageFeignClientProperties;
import feign.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@EnableConfigurationProperties(JStorageFeignClientProperties.class)
public class JStorageFeignClientConfiguration {
    private final JStorageFeignClientProperties properties;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                properties.getConnectTimeout().toMillis(),
                TimeUnit.MILLISECONDS,
                properties.getReadTimeout().toMillis(),
                TimeUnit.MILLISECONDS,
                Boolean.FALSE
        );
    }
}
