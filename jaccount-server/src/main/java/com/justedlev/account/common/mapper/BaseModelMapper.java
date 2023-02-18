package com.justedlev.account.common.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class BaseModelMapper extends ModelMapper {
    public BaseModelMapper() {
        this.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }

    @Override
    public <D> D map(Object source, Class<D> destination) {
        return source == null ? null : super.map(source, destination);
    }
}
