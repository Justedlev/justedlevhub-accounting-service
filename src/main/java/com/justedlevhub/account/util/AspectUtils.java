package com.justedlevhub.account.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AspectUtils {
    public static <E> Collection<E> mapToEntities(JoinPoint joinPoint, Class<E> entityType) {
        return Optional.of(joinPoint.getArgs())
                .map(Arrays::stream)
                .flatMap(Stream::findFirst)
                .filter(Iterable.class::isInstance)
                .map(obj -> (Iterable<?>) obj)
                .map(Iterable::spliterator)
                .map(spliterator -> StreamSupport.stream(spliterator, false)
                        .filter(Objects::nonNull)
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
