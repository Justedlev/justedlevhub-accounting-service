package com.justedlev.account.common.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomModelMapper extends ModelMapper {
    public CustomModelMapper() {
        this.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }

    @Override
    public <D> D map(Object source, Class<D> destination) {
        return Objects.isNull(source) ? null : super.map(source, destination);
    }
}
