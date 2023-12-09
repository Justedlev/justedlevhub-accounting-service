package com.justedlev.hub.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    ACTIVE(AccountStatus.PREFIX + "active"),
    UNCONFIRMED(AccountStatus.PREFIX + "unconfirmed"),
    RESTORED(AccountStatus.PREFIX + "restored"),
    DEACTIVATED(AccountStatus.PREFIX + "deactivated"),
    DELETED(AccountStatus.PREFIX + "deleted");

    private static final String PREFIX = "account.status.";

    private final String label;

    public static AccountStatus labelOf(String label) {
        return findByLabel(label).orElse(null);
    }

    public static Optional<AccountStatus> findByLabel(String label) {
        return findByFilter(v -> v.label.equals(label));
    }

    public static AccountStatus labelOfIgnoreCase(String label) {
        return findLabelIgnoreCase(label).orElse(null);
    }

    public static Optional<AccountStatus> findLabelIgnoreCase(String label) {
        return findByFilter(v -> v.label.equalsIgnoreCase(label));
    }

    public static Optional<AccountStatus> findByFilter(Predicate<AccountStatus> filter) {
        return Objects.isNull(filter) ? Optional.empty() : Stream.of(values()).filter(filter).findFirst();
    }
}
