package com.justedlev.account.configuration;

import com.justedlev.account.common.converter.PhoneNumberResponseConverter;
import com.justedlev.account.model.response.PhoneNumberResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ConverterMappersConfiguration {
    private final PhoneNumberResponseConverter phoneNumberResponseConverter;

    @Bean
    public Converter<String, PhoneNumberResponse> string2PhoneNumberResponseConverter() {
        return mappingContext -> phoneNumberResponseConverter.convert(mappingContext.getSource());
    }

    @Bean
    public Converter<LocalDateTime, Timestamp> localDateTime2TimestampConverter() {
        return mappingContext -> Timestamp.valueOf(mappingContext.getSource());
    }
}
