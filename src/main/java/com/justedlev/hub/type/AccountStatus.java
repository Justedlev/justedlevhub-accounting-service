package com.justedlev.hub.type;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

public enum AccountStatus {
    ACTUAL, UNCONFIRMED, RESTORED, DEACTIVATED, DELETED;

    public static Optional<AccountStatus> getByName(String name) {
        return Optional.ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .flatMap(current -> Stream.of(AccountStatus.values())
                        .filter(roleType -> roleType.name().equalsIgnoreCase(current))
                        .findFirst());
    }
}
