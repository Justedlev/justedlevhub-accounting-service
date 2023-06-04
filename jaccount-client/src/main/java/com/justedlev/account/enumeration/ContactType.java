package com.justedlev.account.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContactType {
    EMAIL("email"),
    PHONE("phone");

    private final String value;
}
