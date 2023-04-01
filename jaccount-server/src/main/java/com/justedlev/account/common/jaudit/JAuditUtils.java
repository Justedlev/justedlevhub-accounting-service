package com.justedlev.account.common.jaudit;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class JAuditUtils {
    private JAuditUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Collection<?> mapToEntities(JoinPoint jp) {
        return Optional.of(jp.getArgs())
                .filter(ArrayUtils::isNotEmpty)
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .filter(Iterable.class::isInstance)
                .map(current -> (Iterable<?>) current)
                .map(current -> StreamSupport.stream(current.spliterator(), true))
                .stream()
                .flatMap(Function.identity())
                .filter(current -> current.getClass().isAnnotationPresent(Entity.class))
                .filter(current -> current.getClass().isAnnotationPresent(JAuditable.class))
                .toList();
    }

    public static <E> Collection<E> mapToEntities(JoinPoint jp, Class<E> clazz) {
        return mapToEntity(jp)
                .stream()
                .map(clazz::cast)
                .toList();
    }

    public static Optional<?> mapToEntity(JoinPoint jp) {
        return Optional.of(jp.getArgs())
                .filter(ArrayUtils::isNotEmpty)
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .filter(current -> current.getClass().isAnnotationPresent(Entity.class))
                .filter(current -> current.getClass().isAnnotationPresent(JAuditable.class));
    }

    public static <E> Optional<E> mapToEntity(JoinPoint jp, Class<E> clazz) {
        return mapToEntity(jp)
                .map(clazz::cast);
    }
}
