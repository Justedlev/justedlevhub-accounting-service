package com.justedlev.account.configuration;

import com.justedlev.account.properties.JAccountProperties;
import com.justedlev.notification.properties.CloudAmqpProperties;
import com.justedlev.notification.properties.JNotificationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JAccountProperties.class,
        JAccountProperties.Service.class,
        JNotificationProperties.class,
        JNotificationProperties.Service.class,
        CloudAmqpProperties.class,
        CloudAmqpProperties.Queues.class
})
public class PropertiesConfiguration {
}
