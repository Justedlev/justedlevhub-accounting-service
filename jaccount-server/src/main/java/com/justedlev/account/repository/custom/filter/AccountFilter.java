package com.justedlev.account.repository.custom.filter;

import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountFilter {
    private Collection<UUID> ids;
    private Collection<String> nicknames;
    private Collection<String> emails;
    private Collection<AccountStatusCode> statuses;
    private Collection<ModeType> modes;
    private Collection<String> activationCodes;
    private Timestamp modeAtFrom;
    private Timestamp modeAtTo;
}
