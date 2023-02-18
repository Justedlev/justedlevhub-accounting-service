package com.justedlev.account.common.mapper.impl;

import com.justedlev.account.common.converter.PhoneNumberConverter;
import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.common.mapper.BaseModelMapper;
import com.justedlev.account.model.Avatar;
import com.justedlev.account.model.PhoneNumberInfo;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final ModelMapper mapper = new BaseModelMapper();
    private final PhoneNumberConverter phoneNumberConverter;

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    public AccountResponse map(Account request) {
        return mapper.map(request, AccountResponse.class);
    }

    @Override
    public Account map(AccountRequest request) {
        return mapper.map(request, Account.class);
    }

    @PostConstruct
    private void init() {
        mapper.createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegistrationDate)
                .addMapping(this::getAvatarUrl, AccountResponse::setAvatarUrl);
        mapper.createTypeMap(AccountRequest.class, Account.class)
                .addMapping(this::convertToPhoneInfo, Account::setPhoneNumberInfo);
    }

    private String getAvatarUrl(Account account) {
        return Optional.of(account)
                .map(Account::getAvatar)
                .map(Avatar::getUrl)
                .orElse(null);
    }

    private PhoneNumberInfo convertToPhoneInfo(AccountRequest accountRequest) {
        return Optional.ofNullable(accountRequest)
                .map(AccountRequest::getPhoneNumber)
                .filter(StringUtils::isNotBlank)
                .map(phoneNumberConverter::convert)
                .orElse(null);
    }
}
