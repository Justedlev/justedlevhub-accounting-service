package com.justedlev.hub.model.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterParams implements Serializable {
    private Collection<String> statuses;
    private Collection<String> modes;
    private String q;
}
