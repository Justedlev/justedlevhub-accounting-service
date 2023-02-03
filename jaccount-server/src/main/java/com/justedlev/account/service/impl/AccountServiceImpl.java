package com.justedlev.account.service.impl;

import com.justedlev.account.client.EndpointConstant;
import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.common.mapper.ReportMapper;
import com.justedlev.account.component.AccountComponent;
import com.justedlev.account.component.AccountModeComponent;
import com.justedlev.account.component.PageCounterComponent;
import com.justedlev.account.constant.ExceptionConstant;
import com.justedlev.account.constant.MailSubjectConstant;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.properties.JAccountProperties;
import com.justedlev.account.repository.custom.filter.AccountFilter;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.service.AccountService;
import com.justedlev.common.entity.BaseEntity_;
import com.justedlev.common.model.request.PaginationRequest;
import com.justedlev.common.model.response.PageResponse;
import com.justedlev.common.model.response.ReportResponse;
import com.justedlev.notification.model.request.SendTemplateMailRequest;
import com.justedlev.notification.queue.JNotificationQueue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountComponent accountComponent;
    private final AccountMapper accountMapper;
    private final ReportMapper reportMapper;
    private final PageCounterComponent pageCounterComponent;
    private final AccountModeComponent accountModeComponent;
    private final JNotificationQueue notificationQueue;
    private final JAccountProperties properties;

    @Override
    public PageResponse<List<AccountResponse>> getPage(PaginationRequest request) {
        var pageCount = pageCounterComponent.accountPageCount(request.getSize());

        if (pageCount < request.getPage()) {
            throw new IllegalArgumentException(String.format("Maximum pages is %s", pageCount));
        }

        var page = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                Sort.Direction.DESC,
                BaseEntity_.CREATED_AT
        );
        var filter = AccountFilter.builder()
                .pageable(page)
                .build();
        var accounts = accountComponent.getByFilter(filter);
        var data = accountMapper.mapToResponse(accounts);

        return PageResponse.<List<AccountResponse>>builder()
                .page(request.getPage())
                .maxPages(pageCount)
                .data(data)
                .build();
    }

    @Override
    public AccountResponse getByEmail(String email) {
        var filter = AccountFilter.builder()
                .emails(Set.of(email))
                .build();
        var account = accountComponent.getByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, email)));

        return accountMapper.mapToResponse(account);
    }

    @Override
    public AccountResponse getByNickname(String nickname) {
        var account = accountComponent.getByNickname(nickname)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionConstant.USER_NOT_EXISTS, nickname)));

        return accountMapper.mapToResponse(account);
    }

    @Override
    public ReportResponse confirm(String code) {
        var account = accountComponent.confirm(code);

        return reportMapper.toReport(String.format("User %s confirmed account", account.getNickname()));
    }

    @Override
    public AccountResponse update(String nickname, AccountRequest request) {
        var account = accountComponent.update(nickname, request);

        return accountMapper.mapToResponse(account);
    }

    @Override
    public AccountResponse updateAvatar(String nickname, MultipartFile photo) {
        var account = accountComponent.update(nickname, photo);
        var response = accountMapper.mapToResponse(account);
        response.setAvatarUrl(account.getAvatar().getUrl());

        return response;
    }

    @Override
    public List<AccountResponse> updateMode(UpdateAccountModeRequest request) {
        return accountModeComponent.updateMode(request);
    }

    @Override
    public AccountResponse create(AccountRequest request) {
        var account = accountComponent.create(request);
        var saved = accountComponent.save(account);
        sendConfirmationEmail(saved);

        return accountMapper.mapToResponse(saved);
    }

    @SneakyThrows
    private void sendConfirmationEmail(Account account) {
        var confirmationLink = UriComponentsBuilder.fromHttpUrl(properties.getService().getHost())
                .path(EndpointConstant.V1_ACCOUNT_CONFIRM)
                .path("/" + account.getActivationCode())
                .build().toUriString();
        var content = Map.of(
                "{FULL_NAME}", account.getNickname(),
                "{CONFIRMATION_LINK}", confirmationLink,
                "{BEST_REGARDS_FROM}", properties.getService().getName()
        );
        var mail = SendTemplateMailRequest.builder()
                .recipient(account.getEmail())
                .subject(String.format(MailSubjectConstant.CONFIRMATION, properties.getService().getName()))
                .templateName("account-confirmation")
                .content(content)
                .build();
        notificationQueue.sendEmail(mail);
    }
}
