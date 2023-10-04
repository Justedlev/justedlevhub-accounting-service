package com.justedlev.hub.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.justedlev.hub.type.AccountMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateAccountModeRequest implements Serializable {
    @NotNull
    @NotEmpty
    private Collection<@NotNull AccountMode> fromAccountModes;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccountMode toAccountMode;
}
