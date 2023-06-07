package com.justedlevhub.account.configuration;

import com.justedlevhub.account.properties.CloudAmqpProperties;
import com.justedlevhub.account.properties.JAccountProperties;
import com.justedlevhub.account.properties.JNotificationProperties;
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
