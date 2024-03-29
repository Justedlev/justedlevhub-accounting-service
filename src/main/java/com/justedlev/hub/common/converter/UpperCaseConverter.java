package com.justedlev.hub.common.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Locale;
import java.util.Optional;

public class UpperCaseConverter extends StdConverter<String, String> {
    @Override
    public String convert(String value) {
        return Optional.ofNullable(value)
                .map(current -> current.toUpperCase(Locale.ROOT))
                .orElse(null);
    }
}
