package com.justedlev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.TreeMap;
import java.util.function.Supplier;

@UtilityClass
public class SimpleMapSuppliers {
    @NonNull
    public static <T> Supplier<TreeMap<String, T>> caseInsensitive() {
        return () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
}
