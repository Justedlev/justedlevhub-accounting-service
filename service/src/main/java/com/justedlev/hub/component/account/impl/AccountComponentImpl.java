package com.justedlev.hub.component.account.impl;

import com.justedlev.hub.component.account.AccountComponent;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.filter.AccountFilter;
import com.justedlev.hub.repository.specification.AccountSpecification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.justedlev.hub.type.AccountStatus.ACTIVE;
import static com.justedlev.hub.type.AccountStatus.UNCONFIRMED;

@Component
@RequiredArgsConstructor
public class AccountComponentImpl implements AccountComponent {
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

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
    public Account confirm(String code) {
        var account = accountRepository.findByConfirmCodeAndStatus(code, UNCONFIRMED.getLabel())
                .orElseThrow(() -> new EntityNotFoundException("Already activated"));
        account.setStatus(ACTIVE.getLabel());

        return save(account);
    }

    @Override
    public Account deactivate(String nickname) {
        return null;
    }

    @Override
    public Account activate(String nickname) {
        return null;
    }

    @Override
    public Account create(CreateAccountRequest request) {
        var filter = AccountFilter.builder()
                .nickname(request.getNickname())
                .build();

        if (accountRepository.exists(AccountSpecification.from(filter))) {
            throw new EntityExistsException(
                    String.format("Account %s already exists", request.getNickname()));
        }

        var account = mapper.map(request, Account.class);

        return accountRepository.save(account);
    }

    @Override
    public Account save(Account entity) {
        return Optional.ofNullable(entity)
                .map(accountRepository::save)
                .orElse(null);
    }

    @Override
    public List<Account> saveAll(List<Account> entities) {
        return Optional.ofNullable(entities)
                .filter(CollectionUtils::isNotEmpty)
                .map(accountRepository::saveAll)
                .orElse(Collections.emptyList());
    }

    @Override
    public void delete(Account entity) {
        accountRepository.delete(entity);
    }

    @Override
    public Optional<Account> findByNickname(String nickname) {
        return Optional.ofNullable(nickname)
                .filter(StringUtils::isNotBlank)
                .map(Set::of)
                .map(current -> AccountFilter.builder()
                        .nicknames(current)
                        .build())
                .map(this::findByFilter)
                .stream()
                .flatMap(Collection::stream)
                .max(Comparator.comparing(Account::getCreatedAt));
    }

    @Override
    public Account updateByNickname(String nickname, UpdateAccountRequest request) {
        var account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("Not exist"));
        mapper.map(request, account);

        return accountRepository.save(account);
    }
}
