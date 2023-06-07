package com.justedlevhub.account.common.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class CustomModelMapper extends ModelMapper {
    public CustomModelMapper(Set<Converter<?, ?>> converters) {
        converters.forEach(this::addConverter);
        this.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }

    @Override
    public <D> D map(Object source, Class<D> destination) {
        return Objects.isNull(source) ? null : super.map(source, destination);
    }
}
