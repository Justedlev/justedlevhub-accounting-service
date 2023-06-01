package com.justedlev.account.repository.specification.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactFilter {
    private Collection<String> emails;
    private String searchText;
}
