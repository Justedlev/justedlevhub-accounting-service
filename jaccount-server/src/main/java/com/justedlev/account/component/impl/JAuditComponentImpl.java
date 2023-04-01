package com.justedlev.account.component.impl;

import com.justedlev.account.component.JAuditComponent;
import com.justedlev.account.repository.JAuditRepository;
import com.justedlev.account.repository.custom.filter.JAuditFilter;
import com.justedlev.account.repository.entity.JAudit;
import com.justedlev.common.model.request.PaginationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JAuditComponentImpl implements JAuditComponent {
    private final JAuditRepository jAuditRepository;

    @Override
    public List<JAudit> getByFilter(JAuditFilter filter) {
        return null;
    }

    @Override
    public Page<JAudit> getPageByFilter(JAuditFilter filter, PaginationRequest paginationRequest) {
        return null;
    }
}
