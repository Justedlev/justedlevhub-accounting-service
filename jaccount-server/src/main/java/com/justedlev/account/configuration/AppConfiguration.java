package com.justedlev.account.configuration;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PhoneNumberUtil phoneNumberUtil() {
        return PhoneNumberUtil.getInstance();
    }

    @Bean
    public EmailValidator emailValidator() {
        return EmailValidator.getInstance();
    }
}
