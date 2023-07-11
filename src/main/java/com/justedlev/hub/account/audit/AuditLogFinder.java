package com.justedlev.hub.account.audit;

import com.justedlev.hub.account.audit.repository.entity.AuditLog;
import com.justedlev.hub.account.audit.repository.specification.filter.AuditLogFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AuditLogFinder {
    List<AuditLog> findByFilter(AuditLogFilter filter);

    List<AuditLog> findByEntityIds(Collection<String> entityIds);

    Map<String, AuditLog> findGroupByEntityIds(Collection<String> entityIds);
}
