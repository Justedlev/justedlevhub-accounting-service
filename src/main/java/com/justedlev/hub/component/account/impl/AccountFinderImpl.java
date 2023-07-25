package com.justedlev.hub.component.account.impl;

import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.specification.AccountSpecification;
import com.justedlev.hub.repository.specification.filter.AccountFilter;
import com.justedlev.hub.component.account.AccountFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountFinderImpl implements AccountFinder {
    private final AccountRepository accountRepository;

    @Override
    public List<Account> findByFilter(AccountFilter filter) {
        return Optional.ofNullable(filter)
                .map(AccountSpecification::from)
                .map(accountRepository::findAll)
                .orElse(Collections.emptyList());
    }

    @Override
    public Page<Account> findPageByFilter(AccountFilter filter, Pageable pageable) {
        return accountRepository.findAll(AccountSpecification.from(filter), pageable);
    }

    @Override
    public Optional<Account> findByNickname(String nickname) {
        return accountRepository.findByNickname(nickname)
                .stream()
                .max(Comparator.comparing(Account::getCreatedAt));
    }
}
