package com.justedlev.account.repository.specification.filter;

import com.justedlev.account.repository.entity.embedabble.AccountContactId;
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
public class AccountContactFilter implements Serializable {
    private Collection<AccountContactId> ids;
    private Boolean main;
}
