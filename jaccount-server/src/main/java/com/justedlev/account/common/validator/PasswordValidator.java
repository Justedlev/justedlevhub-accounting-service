package com.justedlev.account.common.validator;

public interface PasswordValidator {
    void validate(String hash, String rawPassword);
}
