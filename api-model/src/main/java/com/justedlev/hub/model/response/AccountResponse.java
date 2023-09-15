package com.justedlev.hub.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.hub.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private UUID id;
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    private String status;
    private String mode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    private String avatar;
}
