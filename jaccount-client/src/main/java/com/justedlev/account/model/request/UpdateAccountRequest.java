package com.justedlev.account.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.account.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAccountRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime birthDate;
    private Gender gender;
}
