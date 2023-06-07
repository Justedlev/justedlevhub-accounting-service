package com.justedlevhub.account.common.mapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class LocalDateTime2Timestamp implements Converter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convert(MappingContext<LocalDateTime, Timestamp> mappingContext) {
        return Optional.ofNullable(mappingContext.getSource())
                .map(Timestamp::valueOf)
                .orElse(null);
    }
}
