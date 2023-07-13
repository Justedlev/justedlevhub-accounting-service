package com.justedlev.hub.account.common.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Objects;
import java.util.Set;

public class BaseModelMapper extends ModelMapper {
    public BaseModelMapper() {
        getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }

    @Override
    public <D> D map(Object source, Class<D> destination) {
        return Objects.isNull(source) ? null : super.map(source, destination);
    }

    public void addConverters(Set<? extends Converter<?, ?>> converters) {
        if (CollectionUtils.isNotEmpty(converters)) {
            converters.stream()
                    .filter(Objects::nonNull)
                    .forEach(super::addConverter);
        }
    }
}
