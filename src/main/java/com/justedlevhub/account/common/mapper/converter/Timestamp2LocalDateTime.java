package com.justedlevhub.account.common.mapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class Timestamp2LocalDateTime implements Converter<Timestamp, LocalDateTime> {
    @Override
    public LocalDateTime convert(MappingContext<Timestamp, LocalDateTime> mappingContext) {
        return Optional.ofNullable(mappingContext.getSource())
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }
}
