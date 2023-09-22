package com.justedlev.hub.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ContactType {
    EMAIL("email"),
    PHONE_NUMBER("phone-number");

    private final String label;

    public static ContactType labelOf(String label) {
        return findByLabel(label).orElseThrow(() -> new IllegalArgumentException("No enum by label " + label));
    }

    public static Optional<ContactType> findByLabel(String label) {
        return findByFilter(v -> v.label.equals(label));
    }

    public static ContactType labelOfIgnoreCase(String label) {
        return findLabelIgnoreCase(label).orElseThrow(() -> new IllegalArgumentException("No enum by label " + label));
    }

    public static Optional<ContactType> findLabelIgnoreCase(String label) {
        return findByFilter(v -> v.label.equalsIgnoreCase(label));
    }

    public static Optional<ContactType> findByFilter(Predicate<ContactType> filter) {
        return Objects.isNull(filter) ? Optional.empty() : Stream.of(values()).filter(filter).findFirst();
    }
}
