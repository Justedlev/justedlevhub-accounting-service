package com.justedlev.account.common.mapper.impl;

import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final TypeMap<Account, AccountResponse> account2AccountResponse;
    private final TypeMap<AccountRequest, Account> accountRequest2Account;

    @Override
    public AccountResponse map(Account account) {
        return account2AccountResponse.map(account);
    }

    @Override
    public void map(AccountRequest request, Account account) {
        accountRequest2Account.map(request, account);
    }

    @Override
    public Account map(AccountRequest request) {
        return accountRequest2Account.map(request);
    }
}
