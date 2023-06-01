package com.justedlev.account.component.impl;

import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.component.AccountComponent;
import com.justedlev.account.constant.ExceptionConstant;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.enumeration.ModeType;
import com.justedlev.account.model.Avatar;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.repository.AccountRepository;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.specification.AccountSpecification;
import com.justedlev.account.repository.specification.filter.AccountFilter;
import com.justedlev.storage.client.JStorageFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AccountComponentImpl implements AccountComponent {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final JStorageFeignClient storageFeignClient;
    private final ModelMapper baseMapper;

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
    public Page<Account> findPage(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account confirm(String activationCode) {
        validateActivationCode(activationCode);
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
    public Account update(String nickname, AccountRequest request) {
        if (isNicknameTaken(request.getNickname())) {
            throw new EntityExistsException(String.format(ExceptionConstant.NICKNAME_TAKEN, nickname));
        }

        var filter = AccountFilter.builder()
                .nicknames(Set.of(nickname))
                .build();
        var accounts = findByFilter(filter);
        var account = accounts.stream()
                .filter(current -> current.getNickname().equalsIgnoreCase(nickname))
                .max(Comparator.comparing(Account::getCreatedAt))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return update(account, request);
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
    public Account create(AccountRequest request) {
//        var account = findByNickname(request.getNickname())
//                .or(() -> findByEmail(request.getEmail()))
//                .filter(current -> !current.getStatus().equals(AccountStatusCode.DELETED));
//
//        if (account.isPresent()) {
//            throw new EntityExistsException(
//                    String.format("Account %s already exists", request.getNickname()));
//        }

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
    public Account update(Account entity, AccountRequest request) {
        accountMapper.getMapper().map(request, entity);

        return save(entity);
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

    @Override
    @SneakyThrows
    public Account update(String nickname, MultipartFile photo) {
        var account = findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));
        Optional.ofNullable(account.getAvatar())
                .map(Avatar::getFileId)
                .ifPresent(storageFeignClient::delete);
        storageFeignClient.upload(List.of(photo))
                .stream()
                .findFirst()
                .map(current -> baseMapper.map(current, Avatar.class))
                .ifPresent(account::setAvatar);

        return save(account);
    }

    private void validateActivationCode(String activationCode) {
        if (StringUtils.isBlank(activationCode))
            throw new IllegalArgumentException("Code not valid");
    }

    private boolean isNicknameTaken(String nickname) {
        if (StringUtils.isBlank(nickname)) {
            return false;
        }

        var filter = AccountFilter.builder()
                .nicknames(Set.of(nickname))
                .build();

        return CollectionUtils.isNotEmpty(findByFilter(filter));
    }
}
