package com.justedlev.hub.repository.filter;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    private Collection<String> statuses;
    @Singular
    private Collection<String> excludeStatuses;
    @Singular
    private Collection<String> modes;
    @Singular
    private Collection<String> confirmCodes;
    private String freeText;
}
