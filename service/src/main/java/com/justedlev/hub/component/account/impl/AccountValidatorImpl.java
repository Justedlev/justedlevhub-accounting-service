package com.justedlev.hub.component.account.impl;

import com.justedlev.hub.component.account.AccountValidator;
import com.justedlev.hub.repository.AccountRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidatorImpl implements AccountValidator {
    private final AccountRepository accountRepository;

    @Override
    public void validateNickname(String nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new EntityExistsException(String.format(NICKNAME_ALREADY_EXISTS, nickname));
        }
    }
}
