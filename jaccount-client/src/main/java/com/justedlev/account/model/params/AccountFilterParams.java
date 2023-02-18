package com.justedlev.account.model.params;

import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterParams {
    private Collection<AccountStatusCode> statuses;
    private Collection<ModeType> modes;
    private Timestamp modeAtFrom;
    private Timestamp modeAtTo;
    private String q;
}
