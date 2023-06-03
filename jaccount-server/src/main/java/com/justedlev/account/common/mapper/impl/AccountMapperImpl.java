package com.justedlev.account.common.mapper.impl;

import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final TypeMap<Account, AccountResponse> account2AccountResponse;
    private final ModelMapper mapper;

    @Override
    public AccountResponse map(Account account) {
        return account2AccountResponse.map(account);
    }

    @Override
    public void map(AccountRequest request, Account account) {
        mapper.map(request, account);
    }

    @Override
    public Account map(AccountRequest request) {
        return mapper.map(request, Account.class);
    }
}
