package com.justedlev.account.repository.specification.filter;

import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.ModeType;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountFilter implements Serializable {
    @Singular
    private Collection<UUID> ids;
    @Singular
    private Collection<String> nicknames;
    @Singular
    private Collection<AccountStatusCode> statuses;
    @Singular
    private Collection<AccountStatusCode> excludeStatuses;
    @Singular
    private Collection<ModeType> modes;
    @Singular
    private Collection<String> activationCodes;
    private Timestamp modeAtFrom;
    private Timestamp modeAtTo;
    private String searchText;
}
