package com.justedlev.account.model.request;

import com.justedlev.account.enumeration.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateContactRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    private String value;
    @NotNull
    private ContactType type;
}
