package com.justedlev.hub.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.hub.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String nickname;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private StatusResponse status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ModeResponse mode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime registeredAt;
    private String avatarUrl;
}
