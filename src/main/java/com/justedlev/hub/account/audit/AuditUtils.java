package com.justedlev.hub.account.audit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditUtils {
    public static <E> Collection<E> mapToEntities(Class<E> entityType, Object... targets) {
        return mapToEntities(entityType, List.of(targets));
    }

    public static <E> Collection<E> mapToEntities(Class<E> entityType, Iterable<Object> targets) {
        return StreamSupport.stream(targets.spliterator(), false)
                .map(target -> mapToEntity(entityType, target))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public static <E> Optional<E> mapToEntity(Class<E> entityType, Object target) {
        return Optional.ofNullable(target)
                .filter(entityType::isInstance)
                .map(entityType::cast);
    }
}
