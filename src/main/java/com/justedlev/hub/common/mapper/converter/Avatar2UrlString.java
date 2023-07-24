package com.justedlev.hub.common.mapper.converter;

import com.justedlev.hub.repository.entity.Avatar;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;

public class Avatar2UrlString implements Converter<Avatar, String> {
    @Override
    public String convert(MappingContext<Avatar, String> mappingContext) {
        return Optional.ofNullable(mappingContext.getSource())
                .map(Avatar::getUrl)
                .orElse(null);
    }

    public static Avatar2UrlString getInstance() {
        return new Avatar2UrlString();
    }
}
