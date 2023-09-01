package com.justedlev.hub.service;

import com.justedlev.hub.model.response.AccountAuditResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountHistoryService {
    Page<AccountAuditResponse> getPageById(UUID id, Pageable pageable);
}
