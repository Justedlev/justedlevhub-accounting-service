package com.justedlev.account.service.impl;

import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.common.mapper.ReportMapper;
import com.justedlev.account.component.account.AccountAvatarComponent;
import com.justedlev.account.component.account.AccountComponent;
import com.justedlev.account.component.account.AccountModeComponent;
import com.justedlev.account.constant.ExceptionConstant;
import com.justedlev.account.model.request.AccountFilterRequest;
import com.justedlev.account.model.request.CreateAccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.request.UpdateAccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.specification.filter.AccountFilter;
import com.justedlev.account.service.AccountService;
import com.justedlev.common.model.response.PageResponse;
import com.justedlev.common.model.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional(timeout = 120)
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountAvatarComponent accountAvatarComponent;
    private final AccountComponent accountComponent;
    private final AccountMapper accountMapper;
    private final ReportMapper reportMapper;
    private final AccountModeComponent accountModeComponent;
    private final ModelMapper mapper;

    @Override
    public PageResponse<AccountResponse> findPageByFilter(AccountFilterRequest request) {
        var filter = mapper.map(request, AccountFilter.class);
        var pageable = request.getPage().toPageable();
        var page = accountComponent.findPageByFilter(filter, pageable);

        return PageResponse.from(page, accountMapper::map);
    }

    @Override
    public AccountResponse findByNickname(String nickname) {
        var account = accountComponent.findByNickname(nickname)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return accountMapper.map(account);
    }

    @Override
    public ReportResponse confirm(String code) {
        var account = accountComponent.confirm(code);

        return reportMapper.toReport(String.format("User %s confirmed account", account.getNickname()));
    }

    @Override
    public AccountResponse update(UpdateAccountRequest request) {
        var account = accountComponent.update(request);

        return accountMapper.map(account);
    }

    @Override
    public AccountResponse updateAvatar(String nickname, MultipartFile photo) {
        var account = accountAvatarComponent.updateAvatar(nickname, photo);

        return accountMapper.map(account);
    }

    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        return accountModeComponent.updateMode(request);
    }

    @Override
    public AccountResponse create(CreateAccountRequest request) {
        var account = accountComponent.create(request);

        return accountMapper.map(account);
    }
}
