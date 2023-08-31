package com.justedlev.hub.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContactType {
    EMAIL("email"),
    PHONE_NUMBER("phone number");

    private final String value;
}
