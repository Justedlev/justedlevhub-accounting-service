package com.justedlev.account.repository.specification.filter;

import com.justedlev.account.repository.entity.embedabble.AccountContactId;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountContactFilter implements Serializable {
    @Singular
    private Collection<AccountContactId> ids;
    private Boolean main;
}
