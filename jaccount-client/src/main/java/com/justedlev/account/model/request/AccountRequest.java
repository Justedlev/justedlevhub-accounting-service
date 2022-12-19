package com.justedlev.account.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.model.converter.LowerCaseConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {
    @NotNull(message = "Email cannot be empty.")
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email is not valid.")
    @JsonDeserialize(converter = LowerCaseConverter.class)
    private String email;
    @NotNull(message = "Nickname cannot be empty.")
    @NotEmpty(message = "Nickname cannot be empty.")
    @JsonDeserialize(converter = LowerCaseConverter.class)
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime birthDate;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
}
