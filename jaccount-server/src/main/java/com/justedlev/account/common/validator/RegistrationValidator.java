package com.justedlev.account.common.validator;

import com.justedlev.account.model.request.RegistrationRequest;

public interface RegistrationValidator {
    void validate(RegistrationRequest request);
}
