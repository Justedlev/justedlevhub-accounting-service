package com.justedlev.account.common.converter;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.justedlev.account.model.response.PhoneNumberResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberResponseConverter implements Converter<String, PhoneNumberResponse> {
    private final PhoneNumberUtil phoneNumberUtil;

    @Override
    @SneakyThrows
    public PhoneNumberResponse convert(@NonNull String source) {
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
