package com.justedlev.account.repository.specification.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactFilter implements Serializable {
    private Collection<String> emails;
    private String searchText;
}
