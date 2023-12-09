package com.justedlev.hub.configuration;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.justedlev.common.mapper.CustomModelMapper;
import com.justedlev.hub.configuration.properties.ApplicationProperties;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class ApplicationConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

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
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    UriComponentsBuilder serviceUriBuilder(ApplicationProperties.Service service) {
        return UriComponentsBuilder.fromUriString(service.getUrl());
    }

    @Bean
    ModelMapper modelMapper() {
        return new CustomModelMapper();
    }
}
