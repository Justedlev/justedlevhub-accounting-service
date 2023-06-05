package com.justedlev.account.common.mapper.converter;

import com.justedlev.account.repository.entity.Avatar;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Avatar2String implements Converter<Avatar, String> {
    @Override
    public String convert(MappingContext<Avatar, String> mappingContext) {
        return Optional.ofNullable(mappingContext.getSource())
                .map(Avatar::getUrl)
                .orElse(null);
    }
}
