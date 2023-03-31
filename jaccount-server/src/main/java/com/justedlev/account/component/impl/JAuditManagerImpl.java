package com.justedlev.account.component.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justedlev.account.component.JAuditManager;
import com.justedlev.account.repository.JAuditRepository;
import com.justedlev.account.repository.custom.filter.JAuditFilter;
import com.justedlev.account.repository.entity.JAudit;
import com.justedlev.common.model.request.PaginationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JAuditManagerImpl implements JAuditManager {
    private final JAuditRepository jAuditRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<JAudit> getByFilter(JAuditFilter filter) {
        return null;
    }

    @Override
    public Page<JAudit> getPageByFilter(JAuditFilter filter, PaginationRequest paginationRequest) {
        return null;
    }

    @Override
    @SneakyThrows
    public JAudit save(Object entity, JAudit.Operation operation) {
        var audit = JAudit.builder()
                .entityType(entity.getClass())
                .payload(objectMapper.writeValueAsString(entity))
                .operation(operation)
                .build();

        return jAuditRepository.save(audit);
    }
}
