package com.justedlev.hub.component.account.impl;

import com.justedlev.hub.component.account.AccountValidator;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.filter.AccountFilter;
import com.justedlev.hub.repository.specification.AccountSpecification;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidatorImpl implements AccountValidator {
    private final AccountRepository accountRepository;

    @Override
    public void validateNickname(String nickname) {
        if (StringUtils.isBlank(nickname)) {
            throw new IllegalArgumentException("Incorrect nickname input");
        }

        var filter = AccountFilter.builder()
                .nickname(nickname)
                .build();

        if (accountRepository.exists(AccountSpecification.from(filter))) {
            throw new EntityExistsException(String.format(NICKNAME_ALREADY_EXISTS, nickname));
        }
    }
}
