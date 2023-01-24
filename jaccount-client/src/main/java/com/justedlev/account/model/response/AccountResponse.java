package com.justedlev.account.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.model.Mode;
import com.justedlev.account.model.PhoneNumberInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    private String email;
    private PhoneNumberInfo phoneNumberInfo;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccountStatusCode status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ModeType mode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date registrationDate;
    private String avatarUrl;
}
