package com.justedlev.hub.component.account;

public interface AccountValidator {
    String NICKNAME_ALREADY_EXISTS = "Nickname %s already exists";

    void validateNickname(String nickname);
}
