package com.justedlev.account.common.mapper;

import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;
import org.modelmapper.ModelMapper;

public interface AccountMapper {
    ModelMapper getMapper();

    Account map(AccountRequest request);

    AccountResponse map(Account request);
}
