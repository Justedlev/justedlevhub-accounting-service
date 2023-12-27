package com.justedlev.common.mapper;

import com.github.justedlev.modelmapper.BaseModelMapper;
import com.github.justedlev.modelmapper.converter.date.*;
import com.justedlev.common.mapper.converter.Avatar2UrlString;
import com.justedlev.common.mapper.converter.String2PhoneNumberResponse;
import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.RegistrationRequest;
import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.repository.filter.AccountFilter;

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
                .addMapping(AccountFilterParams::getQ, AccountFilter::setFreeText);

        createTypeMap(RegistrationRequest.class, Contact.class)
                .addMapping(RegistrationRequest::getEmail, Contact::setValue);
    }
}
