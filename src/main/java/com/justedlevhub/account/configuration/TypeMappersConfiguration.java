package com.justedlevhub.account.configuration;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.params.AccountFilterParams;
import com.justedlevhub.api.model.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TypeMappersConfiguration {
    @Bean
    public TypeMap<AccountFilterParams, AccountFilter> accountFilterParams2AccountFilter(ModelMapper mapper) {
        return mapper.createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getQ, AccountFilter::setSearchText);
    }

    @Bean
    public TypeMap<Account, AccountResponse> account2AccountResponse(ModelMapper mapper) {
        return mapper.createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegisteredAt);
    }
}
