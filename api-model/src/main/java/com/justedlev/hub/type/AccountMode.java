package com.justedlev.hub.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AccountMode {
    ONLINE(AccountMode.PREFIX + "online"),
    OFFLINE(AccountMode.PREFIX + "offline"),
    HIDDEN(AccountMode.PREFIX + "hidden"),
    SLEEP(AccountMode.PREFIX + "sleep");

    private static final String PREFIX = "account.mode.";

    @JsonValue
    private final String label;

    public static AccountMode labelOf(String label) {
        return findByLabel(label).orElse(null);
    }

    public static Optional<AccountMode> findByLabel(String label) {
        return findByFilter(value -> value.label.equals(label));
    }

    public static AccountMode labelOfIgnoreCase(String label) {
        return findLabelIgnoreCase(label).orElse(null);
    }

    public static Optional<AccountMode> findLabelIgnoreCase(String label) {
        return findByFilter(value -> value.label.equalsIgnoreCase(label));
    }

    public static Optional<AccountMode> findByFilter(Predicate<AccountMode> filter) {
        return Objects.isNull(filter) ? Optional.empty() : Stream.of(values()).filter(filter).findFirst();
    }
}
