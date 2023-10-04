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
public class ContactResponse implements Serializable {
    @Builder.Default
    private boolean main = Boolean.FALSE;
    private String email;
    private PhoneNumberResponse phoneNumber;
}
