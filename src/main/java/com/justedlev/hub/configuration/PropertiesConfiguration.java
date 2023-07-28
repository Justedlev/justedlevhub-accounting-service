package com.justedlev.hub.configuration;

import com.justedlev.hub.properties.CloudAmqpProperties;
import com.justedlev.hub.properties.JAccountProperties;
import com.justedlev.hub.properties.JNotificationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JAccountProperties.class,
        JAccountProperties.Service.class,
        JNotificationProperties.class,
        JNotificationProperties.Service.class,
        CloudAmqpProperties.class,
        CloudAmqpProperties.Queue.class
})
public class PropertiesConfiguration {
}
