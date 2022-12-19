package com.justedlev.account.common.converter;

import com.justedlev.account.model.PhoneNumberInfo;

public interface PhoneNumberConverter {
    PhoneNumberInfo convert(String phoneNumber);
}
