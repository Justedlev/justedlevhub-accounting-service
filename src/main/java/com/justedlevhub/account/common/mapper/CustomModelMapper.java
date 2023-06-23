package com.justedlevhub.account.common.mapper;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.params.AccountFilterParams;
import com.justedlevhub.api.model.response.AccountResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class CustomModelMapper extends ModelMapper {
    public CustomModelMapper(Set<Converter<?, ?>> converters) {
        this.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        converters.forEach(this::addConverter);
        registerTypeMaps();
    }

    private void registerTypeMaps() {
        this.createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getQ, AccountFilter::setSearchText);
        this.createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegisteredAt);
    }

    @Override
    public <D> D map(Object source, Class<D> destination) {
        return Objects.isNull(source) ? null : super.map(source, destination);
    }
}
