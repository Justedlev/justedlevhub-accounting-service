package com.justedlevhub.account.component.account;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountFinder {
    List<Account> findByFilter(AccountFilter filter);

    Page<Account> findPageByFilter(AccountFilter filter, Pageable pageable);

    Optional<Account> findByNickname(String nickname);
}
