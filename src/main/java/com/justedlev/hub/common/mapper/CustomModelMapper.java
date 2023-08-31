package com.justedlev.hub.common.mapper;

import com.github.justedlev.modelmapper.BaseModelMapper;
import com.github.justedlev.modelmapper.converter.date.LocalDate2Timestamp;
import com.github.justedlev.modelmapper.converter.date.LocalDateTime2Timestamp;
import com.github.justedlev.modelmapper.converter.date.Timestamp2LocalDate;
import com.github.justedlev.modelmapper.converter.date.Timestamp2LocalDateTime;
import com.justedlev.hub.common.mapper.converter.Avatar2UrlString;
import com.justedlev.hub.common.mapper.converter.String2PhoneNumberResponse;
import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.RegistrationRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.repository.filter.AccountFilter;
import com.justedlev.hub.type.ContactType;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomModelMapper extends BaseModelMapper {
    public CustomModelMapper() {
        registerTypeMaps();
        addConverters(
                Set.of(
                        LocalDateTime2Timestamp.getInstance(),
                        Timestamp2LocalDateTime.getInstance(),
                        LocalDate2Timestamp.getInstance(),
                        Timestamp2LocalDate.getInstance(),
                        Avatar2UrlString.getInstance(),
                        String2PhoneNumberResponse.getInstance()
                )
        );
    }

    private void registerTypeMaps() {
        createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getFreeText, AccountFilter::setFreeText);
        createTypeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getCreatedAt, AccountResponse::setRegisteredAt);
        createTypeMap(RegistrationRequest.class, Contact.class)
                .addMapping(RegistrationRequest::getEmail, Contact::setValue)
                .addMapping(rr -> ContactType.EMAIL, Contact::setType);
    }
}
