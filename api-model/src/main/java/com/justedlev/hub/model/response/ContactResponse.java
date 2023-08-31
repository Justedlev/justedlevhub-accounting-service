package com.justedlev.hub.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    @Builder.Default
    private boolean main = Boolean.FALSE;
    private String email;
    private PhoneNumberResponse phoneNumber;
}
