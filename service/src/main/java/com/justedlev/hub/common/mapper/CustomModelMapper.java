package com.justedlev.hub.common.mapper;

import com.github.justedlev.modelmapper.BaseModelMapper;
import com.github.justedlev.modelmapper.converter.date.*;
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

@Component
public class CustomModelMapper extends BaseModelMapper {
    public CustomModelMapper() {
        registerTypeMaps();
        addConverters(
                LocalDateTime2Timestamp.getInstance(),
                Timestamp2LocalDateTime.getInstance(),
                LocalDate2Timestamp.getInstance(),
                Timestamp2LocalDate.getInstance(),
                Avatar2UrlString.getInstance(),
                String2PhoneNumberResponse.getInstance(),
                Instant2LocalDateTime.getInstance()
        );
    }

    private void registerTypeMaps() {
        createTypeMap(AccountFilterParams.class, AccountFilter.class)
                .addMapping(AccountFilterParams::getFreeText, AccountFilter::setFreeText);
        createTypeMap(RegistrationRequest.class, Contact.class)
                .addMapping(RegistrationRequest::getEmail, Contact::setValue)
                .addMapping(rr -> ContactType.EMAIL, Contact::setType);
    }

    public AccountResponse map(Account account) {
        return map(account, AccountResponse.class);
    }

    public Account map(AccountResponse accountResponse) {
        return map(accountResponse, Account.class);
    }
}
