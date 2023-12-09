package com.justedlev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.function.BinaryOperator;

@UtilityClass
public class SimpleFunctions {
    @NonNull
    public static <T> BinaryOperator<T> foldRight() {
        return (v1, v2) -> v2;
    }

    @NonNull
    public static <T> BinaryOperator<T> foldLeft() {
        return (v1, v2) -> v1;
    }
}
