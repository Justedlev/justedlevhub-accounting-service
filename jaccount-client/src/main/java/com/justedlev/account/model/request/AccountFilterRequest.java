package com.justedlev.account.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.Gender;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.common.model.request.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterRequest {
    private String q;
    private Collection<AccountStatusCode> statuses;
    private Collection<ModeType> modes;
    private Collection<Gender> genders;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime modeAtFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime modeAtTo;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAtFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAtTo;
    @NotNull
    private PaginationRequest page;
}
