package com.justedlev.hub.service.impl;

import com.justedlev.hub.common.mapper.CustomModelMapper;
import com.justedlev.hub.component.account.AccountModeComponent;
import com.justedlev.hub.constant.ExceptionConstant;
import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.filter.AccountFilter;
import com.justedlev.hub.repository.specification.AccountSpecification;
import com.justedlev.hub.service.AccountService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

import static com.justedlev.hub.type.AccountStatus.ACTIVE;
import static com.justedlev.hub.type.AccountStatus.UNCONFIRMED;

@Service
@CacheConfig(cacheNames = "accounts")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountModeComponent accountModeComponent;
    private final CustomModelMapper mapper;

    @Override
    public Page<AccountResponse> findPageByFilter(AccountFilterParams params, Pageable pageable) {
        var filter = mapper.map(params, AccountFilter.class);
        var page = accountRepository.findAll(AccountSpecification.from(filter), pageable);

        return page.map(mapper::map);
    }

    @Cacheable
    @Override
    public AccountResponse getByNickname(String nickname) {
        return accountRepository.findByNickname(nickname)
                .stream()
                .findFirst()
                .map(mapper::map)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));
    }

    @CacheEvict(key = "#result.nickname")
    @Override
    public AccountResponse confirm(String code) {
        var account = accountRepository.findByConfirmCodeAndStatus(code, UNCONFIRMED.getLabel())
                .orElseThrow(() -> new EntityNotFoundException("Already activated"));
        account.setStatus(ACTIVE.getLabel());
        accountRepository.save(account);

        return mapper.map(account);
    }

    @CachePut(key = "#nickname")
    @Override
    public AccountResponse updateByNickname(String nickname, UpdateAccountRequest request) {
        var account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("Not exist"));
        mapper.map(request, account);
        accountRepository.save(account);

        return mapper.map(account);
    }

    @SneakyThrows
    @CachePut(key = "#nickname")
    @Override
    public AccountResponse updateAvatar(String nickname, MultipartFile photo) {
//        var avatarData = String.format("data:%s;base64,%s",
//                photo.getContentType(), Base64.getEncoder().encodeToString(photo.getBytes()));
        var account = accountRepository.findByNickname(nickname)
                .orElseThrow(EntityNotFoundException::new);
        account.setAvatar(Base64.getEncoder().encodeToString(photo.getBytes()));
        accountRepository.save(account);

        return mapper.map(account);
    }

    @CacheEvict(allEntries = true)
    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        return accountModeComponent.updateMode(request);
    }

    @CachePut(key = "#result.nickname")
    @Override
    public AccountResponse create(CreateAccountRequest request) {
        if (accountRepository.existsByNickname(request.getNickname())) throw new EntityExistsException("Exists");

        var account = mapper.map(request, Account.class);
        accountRepository.save(account);

        return mapper.map(account);
    }

    @CacheEvict
    @Override
    public void deleteByNickname(String nickname) {
        accountRepository.deleteByNickname(nickname);
    }
}
