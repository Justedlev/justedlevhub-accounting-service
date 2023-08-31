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
public class ContactFilterParams {
    private String freeText;
    private Collection<String> emails;
    private Collection<String> phoneNumbers;
}