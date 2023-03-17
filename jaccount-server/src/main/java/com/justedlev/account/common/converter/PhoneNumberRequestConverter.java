package com.justedlev.account.common.converter;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.justedlev.account.model.request.PhoneNumberRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberRequestConverter implements Converter<String, PhoneNumberRequest> {
    private final PhoneNumberUtil phoneNumberUtil;

    @Override
    @SneakyThrows
    public PhoneNumberRequest convert(@NonNull String source) {
        var parsedPhoneNumber = phoneNumberUtil.parse(source, null);
        var regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedPhoneNumber);
        var national = parsedPhoneNumber.getNationalNumber();
        var international = phoneNumberUtil.format(parsedPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

        return PhoneNumberRequest.builder()
                .regionCode(regionCode)
                .countryCode(parsedPhoneNumber.getCountryCode())
                .national(national)
                .international(international)
                .build();
    }
}
