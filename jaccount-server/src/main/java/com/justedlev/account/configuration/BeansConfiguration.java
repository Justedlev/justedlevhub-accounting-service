package com.justedlev.account.configuration;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.justedlev.notification.properties.CloudAmqpProperties;
import com.justedlev.notification.queue.JNotificationQueue;
import com.justedlev.notification.queue.impl.JNotificationQueueImpl;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfiguration {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PhoneNumberUtil phoneNumberUtil() {
        return PhoneNumberUtil.getInstance();
    }

    @Bean
    EmailValidator emailValidator() {
        return EmailValidator.getInstance();
    }

    @Bean
    JNotificationQueue notificationQueue(AmqpTemplate amqpTemplate, CloudAmqpProperties.Queues queues) {
        return new JNotificationQueueImpl(amqpTemplate, queues);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
