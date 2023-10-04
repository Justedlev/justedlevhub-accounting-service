package com.justedlev.hub.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.justedlev.hub.type.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateContactRequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    private String value;
    @NotNull
    private ContactType type;
    private boolean primary;
}
