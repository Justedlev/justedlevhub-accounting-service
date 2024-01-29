package com.justedlev.hub.service.impl;

import com.justedlev.common.constant.ExceptionConstant;
import com.justedlev.hub.component.account.AccountModeComponent;
import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.specification.AccountSpecification;
import com.justedlev.hub.service.AccountService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.justedlev.hub.type.AccountStatus.ACTIVE;

@Service
@CacheConfig(cacheNames = "accounts")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountModeComponent accountModeComponent;
    private final ModelMapper mapper;

    @Override
    public Page<AccountResponse> findPageByFilter(AccountFilterParams params, Pageable pageable) {
        var spec = mapper.map(params, AccountSpecification.class);
        var page = accountRepository.findAll(spec, pageable);

        return page.map(this::map);
    }

    @Cacheable
    @Override
    public AccountResponse getById(@NotBlank UUID id) {
        return accountRepository.findById(id)
                .stream()
                .findFirst()
                .map(current -> mapper.map(current, AccountResponse.class))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, id)));
    }

    @CacheEvict(key = "#result")
    @Override
    public UUID confirm(String code) {
        if (!accountRepository.isUnconfirmed(code)) {
            throw new EntityNotFoundException("Already confirmed");
        }

        var account = accountRepository.findByConfirmCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Not found by code " + code));
        account.setStatus(ACTIVE);

        return accountRepository.save(account).getId();
    }

    @CachePut(key = "#id")
    @Override
    public AccountResponse updateById(UUID id, UpdateAccountRequest request) {
        var account = accountRepository.getReferenceById(id);
        mapper.map(request, account);
        var saved = accountRepository.save(account);

        return mapper.map(saved, AccountResponse.class);
    }

//    @SneakyThrows
//    @CachePut(key = "#id")
//    @Override
//    public AccountResponse updateAvatar(@NotBlank UUID id, MultipartFile photo) {
//        var avatarData = String.format("data:%s;base64,%s",
//                photo.getContentType(), Base64.getEncoder().encodeToString(photo.getBytes()));
//        var account = accountRepository.findById(id)
//                .orElseThrow(EntityNotFoundException::new);
//        account.setAvatarUrl(avatarData);
//        accountRepository.save(account);
//
//        return mapper.map(account, AccountResponse.class);
//    }

    @CacheEvict(allEntries = true)
    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        return accountModeComponent.updateMode(request);
    }

    @CachePut(key = "#result.id")
    @Override
    public AccountResponse create(CreateAccountRequest request) {
        if (accountRepository.existsByNickname(request.getNickname())) throw new EntityExistsException("Exists");

        var account = mapper.map(request, Account.class);
        accountRepository.save(account);

        return mapper.map(account, AccountResponse.class);
    }

    @CacheEvict
    @Override
    public void deleteById(@NotBlank UUID id) {
        accountRepository.deleteById(id);
    }

    private AccountResponse map(Account account) {
        return mapper.map(account, AccountResponse.class);
    }
}
