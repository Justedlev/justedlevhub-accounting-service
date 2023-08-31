package com.justedlev.hub.repository.filter;

import com.justedlev.hub.type.ContactType;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactFilter implements Serializable {
    private String freeTest;
    @Singular
    private Collection<String> values;
    @Singular
    private Collection<ContactType> types;
}
