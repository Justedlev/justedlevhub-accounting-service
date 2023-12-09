package com.justedlev.hub.model.params;

import lombok.Builder;

import java.util.Collection;

@Builder
public record ContactFilterParams(
        String freeText,
        Collection<String> emails,
        Collection<String> phoneNumbers
) {
}
