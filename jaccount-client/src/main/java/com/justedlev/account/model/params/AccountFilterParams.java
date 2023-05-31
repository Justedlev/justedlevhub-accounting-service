package com.justedlev.account.model.params;

import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterParams {
    private Collection<AccountStatusCode> statuses;
    private Collection<ModeType> modes;
    private LocalDateTime modeAtFrom;
    private LocalDateTime modeAtTo;
    private String q;
}
