package com.justedlev.account.component.aspect;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class AspectUtils {
    private AspectUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <E> Collection<E> mapToEntities(JoinPoint joinPoint, Class<E> entityType) {
        return Optional.of(joinPoint.getArgs())
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .filter(Iterable.class::isInstance)
                .map(obj -> (Iterable<?>) obj)
                .map(Iterable::spliterator)
                .map(spliterator -> StreamSupport.stream(spliterator, false)
                        .filter(entityType::isInstance)
                        .map(entityType::cast)
                        .toList())
                .orElse(Collections.emptyList());
    }

    public static <E> Optional<E> mapToEntity(JoinPoint joinPoint, Class<E> entityType) {
        return Optional.of(joinPoint.getArgs())
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .filter(entityType::isInstance)
                .map(entityType::cast);
    }
}