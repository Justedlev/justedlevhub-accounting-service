package com.justedlev.account.component;

import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AccountContactComposer {
    Map<Account, List<Contact>> findAllByAccountIds(Collection<UUID> accountIds);
}
