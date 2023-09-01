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
    ONLINE("online"),
    OFFLINE("offline"),
    HIDDEN("hidden"),
    SLEEP("sleep");

    @JsonValue
    private final String label;

    public static AccountMode labelOf(String label) {
        return findByFilter(label).orElse(null);
    }

    public static Optional<AccountMode> findByFilter(String label) {
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
