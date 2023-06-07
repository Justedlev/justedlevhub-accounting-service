package com.justedlevhub.account.common.mapper;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.api.model.request.CreateAccountRequest;
import com.justedlevhub.api.model.request.UpdateAccountRequest;
import com.justedlevhub.api.model.response.AccountResponse;

public interface AccountMapper {
    Account map(CreateAccountRequest request);

    AccountResponse map(Account account);

    void map(UpdateAccountRequest request, Account account);
}
