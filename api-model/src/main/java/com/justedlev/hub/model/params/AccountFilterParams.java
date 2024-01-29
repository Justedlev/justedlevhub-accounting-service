package com.justedlev.hub.model.params;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

import static com.justedlev.hub.type.AccountMode.*;
import static com.justedlev.hub.type.AccountStatus.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Query parameters for filter")
public class AccountFilterParams implements Serializable {
    @Parameter(description = "Filtering by list of account status", in = ParameterIn.QUERY)
    @Schema(allowableValues = {ACTIVE, UNCONFIRMED, RESTORED, DELETED, DEACTIVATED,})
    private Collection<String> statuses;

    @Parameter(description = "Filtering by list of account mode", in = ParameterIn.QUERY)
    @Schema(allowableValues = {ONLINE, OFFLINE, SLEEP, HIDDEN,})
    private Collection<String> modes;

    @Parameter(description = "Free text filtering", in = ParameterIn.QUERY)
    private String q;
}
