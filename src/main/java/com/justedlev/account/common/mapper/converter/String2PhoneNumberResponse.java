package com.justedlev.account.common.mapper.converter;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.justedlev.account.model.response.PhoneNumberResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class String2PhoneNumberResponse implements Converter<String, PhoneNumberResponse> {
    private final PhoneNumberUtil phoneNumberUtil;

    @Override
    public PhoneNumberResponse convert(MappingContext<String, PhoneNumberResponse> mappingContext) {
        return Optional.ofNullable(mappingContext.getSource())
                .map(this::toPhoneNumberResponse)
                .orElse(null);
    }

    @SneakyThrows
    private PhoneNumberResponse toPhoneNumberResponse(String source) {
        var parsedPhoneNumber = phoneNumberUtil.parse(source, null);
        var regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedPhoneNumber);
        var national = parsedPhoneNumber.getNationalNumber();
        var international = phoneNumberUtil.format(parsedPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

        return PhoneNumberResponse.builder()
                .regionCode(regionCode)
                .countryCode(parsedPhoneNumber.getCountryCode())
                .national(national)
                .international(international)
                .build();
    }
}
