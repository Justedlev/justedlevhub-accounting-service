package com.justedlev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

@UtilityClass
public class SimpleCollectionSuppliers {
    @NonNull
    public static Supplier<Set<String>> caseInsensitive() {
        return () -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    }
}
