package com.justedlev.hub.account.common.mapper;

import com.github.justedlev.modelmapper.converter.date.LocalDateTime2Timestamp;
import com.github.justedlev.modelmapper.converter.date.Timestamp2LocalDateTime;
import com.justedlev.hub.account.repository.entity.Account;
import com.justedlev.hub.account.repository.entity.Contact;
import com.justedlev.hub.account.repository.specification.filter.AccountFilter;
import com.justedlev.hub.api.model.params.AccountFilterParams;
import com.justedlev.hub.api.model.request.RegistrationRequest;
import com.justedlev.hub.api.model.response.AccountResponse;
import com.justedlev.hub.api.type.ContactType;
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
        converters.forEach(super::addConverter);
        super.addConverter(LocalDateTime2Timestamp.getInstance());
        super.addConverter(Timestamp2LocalDateTime.getInstance());
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
