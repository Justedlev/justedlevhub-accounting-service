package com.justedlev.account.configuration;

import com.justedlev.account.common.mapper.converter.Avatar2String;
import com.justedlev.account.common.mapper.converter.LocalDateTime2Timestamp;
import com.justedlev.account.common.mapper.converter.String2PhoneNumberResponse;
import com.justedlev.account.common.mapper.converter.Timestamp2LocalDateTime;
import com.justedlev.account.model.params.AccountFilterParams;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.model.response.ContactResponse;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.specification.filter.AccountFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TypeMappersConfiguration {
    private final String2PhoneNumberResponse string2PhoneNumberResponse;
    private final LocalDateTime2Timestamp localDateTime2Timestamp;
    private final Timestamp2LocalDateTime timestamp2LocalDateTime;
    private final Avatar2String avatar2String;

    @Bean
    public TypeMap<AccountFilterParams, AccountFilter> accountFilterParams2AccountFilter(ModelMapper modelMapper) {
        return modelMapper.createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getQ, AccountFilter::setSearchText)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(localDateTime2Timestamp)
                );
    }

    @Bean
    public TypeMap<Contact, ContactResponse> contact2ContactResponse(ModelMapper modelMapper) {
        return modelMapper.createTypeMap(Contact.class, ContactResponse.class)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(string2PhoneNumberResponse)
                );
    }

    @Bean
    public TypeMap<Account, AccountResponse> account2AccountResponse(ModelMapper mapper) {
        return mapper.createTypeMap(Account.class, AccountResponse.class)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(timestamp2LocalDateTime)
                )
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(timestamp2LocalDateTime)
                                .map(Account::getCreatedAt, AccountResponse::setRegistrationDate)
                )
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(avatar2String)
                );
    }

    @Bean
    public TypeMap<AccountRequest, Account> accountRequest2Account(ModelMapper mapper) {
        return mapper.createTypeMap(AccountRequest.class, Account.class)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(localDateTime2Timestamp)
                );
    }
}
