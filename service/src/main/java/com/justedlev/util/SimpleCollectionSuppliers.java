package com.justedlev.util;

import lombok.experimental.UtilityClass;

import java.util.TreeSet;
import java.util.function.Supplier;

@UtilityClass
public class SimpleCollectionSuppliers {
    public static Supplier<TreeSet<String>> caseInsensitive() {
        return () -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    }
}
