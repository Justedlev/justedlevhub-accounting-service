package com.justedlev.account.common.mapper;

import com.justedlev.account.model.request.CreateAccountRequest;
import com.justedlev.account.model.request.UpdateAccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;

public interface AccountMapper {
    Account map(CreateAccountRequest request);

    AccountResponse map(Account account);

    void map(UpdateAccountRequest request, Account account);
}
