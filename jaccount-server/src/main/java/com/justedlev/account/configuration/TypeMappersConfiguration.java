package com.justedlev.account.configuration;

import com.justedlev.account.model.Avatar;
import com.justedlev.account.model.params.AccountFilterParams;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.model.response.ContactResponse;
import com.justedlev.account.model.response.PhoneNumberResponse;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.specification.filter.AccountFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class TypeMappersConfiguration {
    private final Converter<String, PhoneNumberResponse> string2PhoneNumberResponseConverter;
    private final Converter<LocalDateTime, Timestamp> localDateTime2TimestampConverter;

    @Bean
    public TypeMap<AccountFilterParams, AccountFilter> accountFilterParams2AccountFilter(ModelMapper modelMapper) {
        return modelMapper.createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getQ, AccountFilter::setSearchText)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(localDateTime2TimestampConverter)
                )
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(localDateTime2TimestampConverter)
                );
    }

    @Bean
    public TypeMap<Account, AccountResponse> account2AccountResponse(ModelMapper modelMapper) {
        return modelMapper.createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegistrationDate)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .map(
                                        source -> Optional.of(source)
                                                .map(Account::getAvatar)
                                                .map(Avatar::getUrl)
                                                .orElse(null),
                                        AccountResponse::setAvatarUrl
                                )
                );
    }

    @Bean
    public TypeMap<Contact, ContactResponse> contact2ContactResponse(ModelMapper modelMapper) {
        return modelMapper.createTypeMap(Contact.class, ContactResponse.class)
                .addMappings(
                        mapping -> mapping
                                .when(Conditions.isNotNull())
                                .using(string2PhoneNumberResponseConverter)
                );
    }
}
