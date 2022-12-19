package com.justedlev.account.common.mapper;

import com.justedlev.account.component.base.EntityMapper;
import com.justedlev.account.component.base.ResponseMapper;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;

public interface AccountMapper extends ResponseMapper<Account, AccountResponse>, EntityMapper<AccountRequest, Account> {
}
