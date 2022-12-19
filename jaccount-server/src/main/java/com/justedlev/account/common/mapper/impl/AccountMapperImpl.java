package com.justedlev.account.common.mapper.impl;

import com.justedlev.account.common.converter.PhoneNumberConverter;
import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.model.Avatar;
import com.justedlev.account.model.PhoneNumberInfo;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final ModelMapper defaultMapper;
    private final PhoneNumberConverter phoneNumberConverter;

    @Override
    public AccountResponse mapToResponse(Account request) {
        var response = defaultMapper.map(request, AccountResponse.class);
        response.setRegistrationDate(request.getCreatedAt());
        Optional.ofNullable(request.getAvatar())
                .map(Avatar::getUrl)
                .ifPresent(response::setAvatarUrl);

        return response;
    }

    @Override
    public List<AccountResponse> mapToResponse(List<Account> requests) {
        return requests.stream()
                .map(this::mapToResponse)
                .distinct()
                .toList();
    }

    @Override
    public Account mapToEntity(AccountRequest request) {
        var account = defaultMapper.map(request, Account.class);
        var phoneNumber = convertToPhoneInfo(request.getPhoneNumber());
        account.setPhoneNumberInfo(phoneNumber);

        return account;
    }

    @Override
    public List<Account> mapToEntity(List<AccountRequest> requests) {
        return requests.parallelStream()
                .map(this::mapToEntity)
                .toList();
    }

    private PhoneNumberInfo convertToPhoneInfo(String phoneNumber) {
        return Optional.ofNullable(phoneNumber)
                .filter(StringUtils::isNotBlank)
                .map(phoneNumberConverter::convert)
                .orElse(null);
    }
}
