package com.justedlev.account.common.validator.impl;

import com.justedlev.account.common.validator.AccountRequestValidator;
import com.justedlev.account.model.request.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRequestValidatorImpl implements AccountRequestValidator {
    @Override
    public void validate(AccountRequest request) {
        if(StringUtils.isBlank(request.getPhoneNumber()))
            throw new IllegalArgumentException("");
    }
}
