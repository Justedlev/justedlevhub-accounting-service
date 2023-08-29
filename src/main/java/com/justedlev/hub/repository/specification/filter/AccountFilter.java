package com.justedlev.hub.repository.specification.filter;

import com.justedlev.hub.api.type.AccountStatus;
import com.justedlev.hub.api.type.ModeType;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AccountFilter implements Serializable {
    @Singular
    private Collection<UUID> ids;
    @Singular
    private Collection<String> nicknames;
    @Singular
    private Collection<AccountStatus> statuses;
    @Singular
    private Collection<AccountStatus> excludeStatuses;
    @Singular
    private Collection<ModeType> modes;
    @Singular
    private Collection<String> activationCodes;
    private Timestamp modeAtFrom;
    private Timestamp modeAtTo;
    private String searchText;
}
