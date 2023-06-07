package com.justedlevhub.account.common.mapper.impl;

import com.justedlevhub.account.common.mapper.AccountMapper;
import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.api.model.request.CreateAccountRequest;
import com.justedlevhub.api.model.request.UpdateAccountRequest;
import com.justedlevhub.api.model.response.AccountResponse;
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
    public void map(UpdateAccountRequest request, Account account) {
        mapper.map(request, account);
    }

    @Override
    public Account map(CreateAccountRequest request) {
        return mapper.map(request, Account.class);
    }
}
