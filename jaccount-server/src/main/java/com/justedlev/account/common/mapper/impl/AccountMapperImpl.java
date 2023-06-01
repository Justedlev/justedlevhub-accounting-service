package com.justedlev.account.common.mapper.impl;

import com.justedlev.account.common.converter.PhoneNumberResponseConverter;
import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.common.mapper.BaseModelMapper;
import com.justedlev.account.model.Avatar;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.model.response.ContactResponse;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final ModelMapper mapper = new BaseModelMapper();
    private final PhoneNumberResponseConverter phoneNumberResponseConverter;

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    public AccountResponse map(Account request) {
        var res = mapper.map(request, AccountResponse.class);
        var contacts = convertToContactResponse(request);
        res.setContacts(contacts);

        return res;
    }

    private Set<ContactResponse> convertToContactResponse(Account request) {
//        return request.getContacts()
//                .stream()
//                .map(current -> {
//                    var res = mapper.map(current, ContactResponse.class);
//                    Optional.ofNullable(current.getPhoneNumber())
//                            .filter(StringUtils::isNotBlank)
//                            .map(phoneNumberResponseConverter::convert)
//                            .ifPresent(res::setPhoneNumber);
//
//                    return res;
//                })
//                .collect(Collectors.toSet());
        return null;
    }

    @Override
    public Account map(AccountRequest request) {
        var account = mapper.map(request, Account.class);
        var contact = convertToContact(request);
//        contact.setAccount(account);
//        account.setContacts(Set.of(contact));

        return account;
    }

    @PostConstruct
    private void init() {
        mapper.createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegistrationDate)
                .addMapping(this::getAvatarUrl, AccountResponse::setAvatarUrl);
    }

    private String getAvatarUrl(Account account) {
        return Optional.of(account)
                .map(Account::getAvatar)
                .map(Avatar::getUrl)
                .orElse(null);
    }

    private Contact convertToContact(AccountRequest accountRequest) {
        return Contact.builder()
                .phoneNumber(accountRequest.getPhoneNumber())
                .email(accountRequest.getEmail())
                .build();
    }
}
