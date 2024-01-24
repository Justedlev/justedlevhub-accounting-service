package com.justedlev.hub.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest implements Serializable {
    @NotBlank
    @Email
    private String email;
    @Size(min = 8, max = 32)
    private String password;
    @NotBlank
    private String nickname;
    private String firstName;
    private String lastName;
}
