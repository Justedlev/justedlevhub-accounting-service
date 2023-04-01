package com.justedlev.account.component;

import com.justedlev.account.repository.custom.filter.JAuditFilter;
import com.justedlev.account.repository.entity.JAudit;
import com.justedlev.common.model.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JAuditComponent {
    List<JAudit> getByFilter(JAuditFilter filter);

    Page<JAudit> getPageByFilter(JAuditFilter filter, PaginationRequest paginationRequest);
}
