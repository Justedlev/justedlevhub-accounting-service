package com.justedlev.account.common.validator;

import com.justedlev.account.model.request.AccountRequest;

public interface AccountRequestValidator {
    void validate(AccountRequest request);
}
