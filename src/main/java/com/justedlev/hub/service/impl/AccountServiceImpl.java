package com.justedlev.hub.service.impl;

import com.justedlev.hub.component.account.AccountAvatarComponent;
import com.justedlev.hub.component.account.AccountComponent;
import com.justedlev.hub.component.account.AccountModeComponent;
import com.justedlev.hub.constant.ExceptionConstant;
import com.justedlev.hub.properties.JAccountProperties;
import com.justedlev.hub.repository.specification.filter.AccountFilter;
import com.justedlev.hub.api.model.params.AccountFilterParams;
import com.justedlev.hub.api.model.request.CreateAccountRequest;
import com.justedlev.hub.api.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.api.model.request.UpdateAccountRequest;
import com.justedlev.hub.api.model.response.AccountResponse;
import com.justedlev.hub.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountAvatarComponent accountAvatarComponent;
    private final AccountComponent accountComponent;
    private final AccountModeComponent accountModeComponent;
    private final ModelMapper mapper;
    private final JAccountProperties properties;

    @Override
    public Page<AccountResponse> findPageByFilter(AccountFilterParams params, Pageable pageable) {
        var filter = mapper.map(params, AccountFilter.class);
        var page = accountComponent.findPageByFilter(filter, pageable);

        return page.map(account -> mapper.map(account, AccountResponse.class));
    }

    @Override
    public AccountResponse findByNickname(String nickname) {
        var account = accountComponent.findByNickname(nickname)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return mapper.map(account, AccountResponse.class);
    }

    @Override
    public String confirm(String code) {
        var account = accountComponent.confirm(code);

        return "redirect:/" + account.getNickname();
    }

    @Override
    public AccountResponse update(UpdateAccountRequest request) {
        var account = accountComponent.update(request);

        return mapper.map(account, AccountResponse.class);
    }

    @Override
    public AccountResponse updateAvatar(String nickname, MultipartFile photo) {
        var account = accountAvatarComponent.updateAvatar(nickname, photo);

        return mapper.map(account, AccountResponse.class);
    }

    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        return accountModeComponent.updateMode(request);
    }

    @Override
    public AccountResponse create(CreateAccountRequest request) {
        var account = accountComponent.create(request);

        return mapper.map(account, AccountResponse.class);
    }
}
