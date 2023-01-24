package com.justedlev.account.component.impl;

import com.justedlev.account.common.mapper.ReportMapper;
import com.justedlev.account.component.AccountComponent;
import com.justedlev.account.component.RegistrationComponent;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.request.RegistrationRequest;
import com.justedlev.common.model.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegistrationComponentImpl implements RegistrationComponent {
    private final ReportMapper reportMapper;
    private final ModelMapper defaultMapper;
    private final AccountComponent accountComponent;

    @Override
    @Transactional
    public ReportResponse registration(RegistrationRequest request) {
        var createAccountRequest = toAccountRequest(request);
        var account = accountComponent.create(createAccountRequest);
        accountComponent.save(account);

        return reportMapper.toReport();
    }

    private AccountRequest toAccountRequest(RegistrationRequest request) {
        return defaultMapper.map(request, AccountRequest.class);
    }
}
