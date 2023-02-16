package com.justedlev.account.repository.custom;

import com.justedlev.account.repository.custom.filter.AccountFilter;
import com.justedlev.account.repository.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountFilterableRepository {
    List<Account> findByFilter(AccountFilter filter);

    Page<Account> findByFilter(AccountFilter filter, Pageable pageable);
}
