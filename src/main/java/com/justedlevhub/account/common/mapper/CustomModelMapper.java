package com.justedlevhub.account.common.mapper;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.params.AccountFilterParams;
import com.justedlevhub.api.model.response.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class CustomModelMapper extends ModelMapper {
    public CustomModelMapper(Set<Converter<?, ?>> converters) {
        this.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        addConverters(converters);
        registerTypeMaps();
    }

    @Override
    public <D> D map(Object source, Class<D> destination) {
        return Objects.isNull(source) ? null : super.map(source, destination);
    }

    private void addConverters(Set<Converter<?, ?>> converters) {
        addConvertersDebug(converters);
        converters.forEach(this::addConverter);
    }

    private void addConvertersDebug(Set<Converter<?, ?>> converters) {
        var converterTypes = converters.stream()
                .map(Converter::getClass)
                .map(Class::getName)
                .toList();
        log.debug("Registration converters: {}", converterTypes);
    }

    private void registerTypeMaps() {
        this.createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getQ, AccountFilter::setSearchText);
        this.createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegisteredAt);
    }
}
