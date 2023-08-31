package com.justedlev.hub.model.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterParams {
    private Collection<Long> statusIds;
    private Collection<Long> modeIds;
    private String freeText;
}
