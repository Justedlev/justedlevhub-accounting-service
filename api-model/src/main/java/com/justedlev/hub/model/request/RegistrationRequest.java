package com.justedlev.hub.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    @Size(min = 8, max = 24)
    private String password;
    @NotNull
    @NotEmpty
    @NotBlank
    private String nickname;
}
