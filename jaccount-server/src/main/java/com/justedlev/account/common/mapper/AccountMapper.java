package com.justedlev.account.common.mapper;

import com.justedlev.account.common.mapper.base.EntityMapper;
import com.justedlev.account.common.mapper.base.ResponseMapper;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;
import org.modelmapper.ModelMapper;

public interface AccountMapper extends ResponseMapper<Account, AccountResponse>, EntityMapper<AccountRequest, Account> {
    ModelMapper getMapper();
}
