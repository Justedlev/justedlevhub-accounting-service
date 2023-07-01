package com.justedlevhub.account.common.mapper;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.entity.Contact;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.params.AccountFilterParams;
import com.justedlevhub.api.model.request.RegistrationRequest;
import com.justedlevhub.api.model.response.AccountResponse;
import com.justedlevhub.api.type.ContactType;
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
        getConfiguration()
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
        createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getQ, AccountFilter::setSearchText);
        createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegisteredAt);
        createTypeMap(RegistrationRequest.class, Contact.class)
                .addMapping(RegistrationRequest::getEmail, Contact::setValue)
                .addMapping(rr -> ContactType.EMAIL, Contact::setType);
    }
}
