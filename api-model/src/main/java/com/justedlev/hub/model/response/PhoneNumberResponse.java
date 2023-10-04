package com.justedlev.hub.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberResponse implements Serializable {
    private Long national;
    private String international;
    private Integer countryCode;
    private String regionCode;
}
