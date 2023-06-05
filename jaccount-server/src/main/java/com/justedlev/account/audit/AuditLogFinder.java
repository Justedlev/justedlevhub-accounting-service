package com.justedlev.account.audit;

import com.justedlev.account.audit.repository.entity.AuditLog;
import com.justedlev.account.audit.repository.specification.filter.AuditLogFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AuditLogFinder {
    List<AuditLog> findByFilter(AuditLogFilter filter);

    List<AuditLog> findByEntityIds(Collection<String> entityIds);

    Map<String, List<AuditLog>> findGroupByEntityIds(Collection<String> entityIds);

    Map<String, AuditLog> findLastGroupByEntityIds(Collection<String> entityIds);
}