package com.justedlev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class SimpleCollectors {
    @NonNull
    public static <T, V> Collector<T, ?, Map<String, V>>
    toCaseInsensitiveMap(@NonNull Function<T, String> keyMapper,
                         @NonNull Function<T, V> valueMapper) {
        return toCaseInsensitiveMap(keyMapper, valueMapper, SimpleFunctions.foldLeft());
    }

    @NonNull
    public static <T> Collector<T, ?, Map<String, T>>
    toCaseInsensitiveMap(@NonNull Function<T, String> keyMapper) {
        return toCaseInsensitiveMap(keyMapper, Function.identity());
    }

    @NonNull
    public static <T> Collector<T, ?, Map<String, List<T>>>
    caseInsensitiveGroupingBy(@NonNull Function<T, String> keyMapper) {
        return caseInsensitiveGroupingBy(keyMapper, Collectors.toList());
    }

    @NonNull
    public static <T, V, D> Collector<T, ?, Map<String, D>>
    caseInsensitiveGroupingBy(@NonNull Function<T, String> keyMapper,
                              @NonNull Collector<T, V, D> downstream) {
        return Collectors.groupingBy(keyMapper, SimpleMapSuppliers.caseInsensitive(), downstream);
    }

    @NonNull
    public static <T, V> Collector<T, ?, Map<String, V>>
    toCaseInsensitiveMap(@NonNull Function<T, String> keyMapper,
                         @NonNull Function<T, V> valueMapper,
                         @NonNull BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction, SimpleMapSuppliers.caseInsensitive());
    }

    @NonNull
    public static Collector<String, ?, Set<String>> toCaseInsensitiveSet() {
        return Collectors.toCollection(SimpleCollectionSuppliers.caseInsensitive());
    }
}
