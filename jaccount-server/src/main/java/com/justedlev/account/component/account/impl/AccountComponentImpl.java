package com.justedlev.account.component.account.impl;

import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.component.account.AccountComponent;
import com.justedlev.account.constant.ExceptionConstant;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.model.request.CreateAccountRequest;
import com.justedlev.account.model.request.UpdateAccountRequest;
import com.justedlev.account.repository.AccountRepository;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.specification.AccountSpecification;
import com.justedlev.account.repository.specification.filter.AccountFilter;
import com.justedlev.storage.client.JStorageFeignClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
    private final JStorageFeignClient storageFeignClient;
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
    public Account confirm(String activationCode) {
        var filter = AccountFilter.builder()
                .activationCodes(Set.of(activationCode))
                .statuses(Set.of(AccountStatusCode.UNCONFIRMED))
                .build();
        var account = accountRepository.findAll(AccountSpecification.from(filter))
                .stream()
                .max(Comparator.comparing(Account::getCreatedAt))
                .orElseThrow(() -> new EntityNotFoundException("Already activated"));
        account.setStatus(AccountStatusCode.ACTUAL);

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
                .excludeStatus(AccountStatusCode.DELETED)
                .build();
        if (accountRepository.exists(AccountSpecification.from(filter))) {
            throw new EntityExistsException(
                    String.format("Account %s already exists", request.getNickname()));
        }

        return accountMapper.map(request);
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
        if (entity.getStatus().equals(AccountStatusCode.DELETED)) {
            throw new IllegalArgumentException(
                    String.format(ExceptionConstant.ACCOUNT_ALREADY_DELETED, entity.getNickname()));
        }

        entity.setStatus(AccountStatusCode.DELETED);
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
                .excludeStatus(AccountStatusCode.DELETED)
                .build();
        var account = accountRepository.findAll(AccountSpecification.from(filter))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Not exist"));
        accountMapper.map(request, account);

        return accountRepository.save(account);
    }
}
