package com.justedlev.account.component.impl;

import com.justedlev.account.common.mapper.AccountMapper;
import com.justedlev.account.component.HistoryComponent;
import com.justedlev.account.model.request.HistoryRequest;
import com.justedlev.account.model.response.AccountHistoryResponse;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.repository.AccountRepository;
import com.justedlev.account.repository.custom.filter.AccountFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistoryComponentImpl implements HistoryComponent {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountHistoryResponse> getAccounts(HistoryRequest request) {
        var filter = AccountFilter.builder()
                .emails(request.getEmails())
                .build();
        var page = PageRequest.of(request.getPageRequest().getPage() - 1,
                request.getPageRequest().getSize(), Sort.Direction.DESC, "createdAt");
        var accounts = accountRepository.findByFilter(filter, page)
                .parallelStream()
                .map(accountMapper::mapToResponse)
                .collect(Collectors.groupingBy(AccountResponse::getEmail));

        return accounts.entrySet().parallelStream()
                .map(current -> AccountHistoryResponse.builder()
                        .email(current.getKey())
                        .accounts(current.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
