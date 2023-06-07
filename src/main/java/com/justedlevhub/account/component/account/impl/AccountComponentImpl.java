package com.justedlevhub.account.component.account.impl;

import com.justedlevhub.account.common.mapper.AccountMapper;
import com.justedlevhub.account.component.account.AccountComponent;
import com.justedlevhub.account.constant.ExceptionConstant;
import com.justedlevhub.account.repository.AccountRepository;
import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.specification.AccountSpecification;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.request.CreateAccountRequest;
import com.justedlevhub.api.model.request.UpdateAccountRequest;
import com.justedlevhub.api.type.AccountStatus;
import com.justedlevhub.api.type.ModeType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AccountComponentImpl implements AccountComponent {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

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
    public Account confirm(String activationCode) {
        var filter = AccountFilter.builder()
                .activationCodes(Set.of(activationCode))
                .statuses(Set.of(AccountStatus.UNCONFIRMED))
                .build();
        var account = accountRepository.findAll(AccountSpecification.from(filter))
                .stream()
                .max(Comparator.comparing(Account::getCreatedAt))
                .orElseThrow(() -> new EntityNotFoundException("Already activated"));
        account.setStatus(AccountStatus.ACTUAL);

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
                .excludeStatus(AccountStatus.DELETED)
                .build();

        if (accountRepository.exists(AccountSpecification.from(filter))) {
            throw new EntityExistsException(
                    String.format("Account %s already exists", request.getNickname()));
        }

        var account = accountMapper.map(request);

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
    public Account delete(Account entity) {
        if (entity.getStatus().equals(AccountStatus.DELETED)) {
            throw new IllegalArgumentException(
                    String.format(ExceptionConstant.ACCOUNT_ALREADY_DELETED, entity.getNickname()));
        }

        entity.setStatus(AccountStatus.DELETED);
        entity.setMode(ModeType.OFFLINE);

        return save(entity);
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

    public Account update(UpdateAccountRequest request) {
        var filter = AccountFilter.builder()
                .nickname(request.getNickname())
                .excludeStatus(AccountStatus.DELETED)
                .build();
        var account = accountRepository.findAll(AccountSpecification.from(filter))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Not exist"));
        accountMapper.map(request, account);

        return accountRepository.save(account);
    }
}
