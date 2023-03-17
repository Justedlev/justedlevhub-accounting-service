package com.justedlev.account.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberRequest {
    private Long national;
    private String international;
    private Integer countryCode;
    private String regionCode;
}
