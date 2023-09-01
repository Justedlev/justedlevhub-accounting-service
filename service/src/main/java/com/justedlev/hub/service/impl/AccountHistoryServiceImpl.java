package com.justedlev.hub.service.impl;

import com.justedlev.hub.model.response.AccountAuditResponse;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.model.response.AuditResponse;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.service.AccountHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService {
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public Page<AccountAuditResponse> getPageById(UUID id, Pageable pageable) {
        return accountRepository.findRevisions(id, pageable).map(this::convert);
    }

    private AccountAuditResponse convert(Revision<Long, Account> revision) {
        return AccountAuditResponse.builder()
                .metadata(mapper.map(revision.getMetadata(), AuditResponse.Metadata.class))
                .audit(mapper.map(revision.getEntity(), AccountResponse.class))
                .build();
    }
}
