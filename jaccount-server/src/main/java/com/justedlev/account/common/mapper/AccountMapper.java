package com.justedlev.account.common.mapper;

import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;

public interface AccountMapper {
    Account map(AccountRequest request);

    AccountResponse map(Account account);

    void map(AccountRequest request, Account account);
}
