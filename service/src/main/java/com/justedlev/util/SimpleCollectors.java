package com.justedlev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class SimpleCollectors {
    @NonNull
    public static <T, V>
    Collector<T, ?, TreeMap<String, V>>
    toCaseInsensitiveMap(@NonNull Function<T, String> keyMapper,
                         @NonNull Function<T, V> valueMapper) {
        return toCaseInsensitiveMap(keyMapper, valueMapper, SimpleFunctions.foldLeft());
    }

    @NonNull
    public static <T>
    Collector<T, ?, TreeMap<String, T>>
    toCaseInsensitiveMap(@NonNull Function<T, String> keyMapper) {
        return toCaseInsensitiveMap(keyMapper, Function.identity());
    }

    @NonNull
    public static <T>
    Collector<T, ?, TreeMap<String, List<T>>>
    caseInsensitiveGroupingBy(@NonNull Function<T, String> keyMapper) {
        return caseInsensitiveGroupingBy(keyMapper, Collectors.toList());
    }

    @NonNull
    public static <T, V, D>
    Collector<T, ?, TreeMap<String, D>>
    caseInsensitiveGroupingBy(@NonNull Function<T, String> keyMapper,
                              @NonNull Collector<T, V, D> downstream) {
        return Collectors.groupingBy(keyMapper, SimpleMapSuppliers.caseInsensitive(), downstream);
    }

    @NonNull
    public static <T, V>
    Collector<T, ?, TreeMap<String, V>>
    toCaseInsensitiveMap(@NonNull Function<T, String> keyMapper,
                         @NonNull Function<T, V> valueMapper,
                         @NonNull BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction, SimpleMapSuppliers.caseInsensitive());
    }

    @NonNull
    public static Collector<String, ?, TreeSet<String>> toCaseInsensitiveSet() {
        return Collectors.toCollection(SimpleCollectionSuppliers.caseInsensitive());
    }
}
