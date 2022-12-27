package com.justedlev.account.client;

public final class PathVariableConstant {
    public static final String NICKNAME = "{nickname}";
    public static final String CODE = "{code}";
    public static final String EMAIL = "{email}";

    private PathVariableConstant() {
        throw new IllegalStateException("Constants class");
    }
}
