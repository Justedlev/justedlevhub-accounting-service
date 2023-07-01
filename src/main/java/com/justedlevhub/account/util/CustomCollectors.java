package com.justedlevhub.account.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomCollectors {
    public static <T, V>
    Collector<T, ?, TreeMap<String, V>>
    toCaseInsensitiveMap(Function<T, String> keyMapper,
                         Function<T, V> valueMapper) {
        return toCaseInsensitiveMap(
                keyMapper,
                valueMapper,
                (v1, v2) -> v1
        );
    }

    public static <T>
    Collector<T, ?, TreeMap<String, List<T>>>
    caseInsensitiveGroupingBy(Function<T, String> keyMapper) {
        return caseInsensitiveGroupingBy(keyMapper, Collectors.toList());
    }

    public static <T, V, D>
    Collector<T, ?, TreeMap<String, D>>
    caseInsensitiveGroupingBy(Function<T, String> keyMapper,
                              Collector<T, V, D> downstream) {
        return Collectors.groupingBy(
                keyMapper,
                () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                downstream
        );
    }

    public static <T, V>
    Collector<T, ?, TreeMap<String, V>>
    toCaseInsensitiveMap(Function<T, String> keyMapper,
                         Function<T, V> valueMapper,
                         BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(
                keyMapper,
                valueMapper,
                mergeFunction,
                () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)
        );
    }

    public static Collector<String, ?, Set<String>> toCaseInsensitiveSet() {
        return Collectors.toCollection(() -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER));
    }
}
