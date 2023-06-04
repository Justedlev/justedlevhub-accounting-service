package com.justedlev.account.repository.specification.filter;

import com.justedlev.account.enumeration.ContactType;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactFilter implements Serializable {
    private String freeSearch;
    @Singular
    private Collection<String> values;
    @Singular
    private Collection<ContactType> types;
}
