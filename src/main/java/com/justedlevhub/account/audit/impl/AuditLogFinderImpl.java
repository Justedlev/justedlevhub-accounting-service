package com.justedlevhub.account.audit.impl;

import com.justedlevhub.account.audit.AuditLogFinder;
import com.justedlevhub.account.audit.repository.AuditLogRepository;
import com.justedlevhub.account.audit.repository.entity.AuditLog;
import com.justedlevhub.account.audit.repository.specification.AuditLogSpecification;
import com.justedlevhub.account.audit.repository.specification.filter.AuditLogFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.justedlevhub.account.util.CustomCollectors.toCaseInsensitiveMap;
import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class AuditLogFinderImpl implements AuditLogFinder {
    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditLog> findByFilter(AuditLogFilter filter) {
        return Optional.ofNullable(filter)
                .map(AuditLogSpecification::from)
                .map(auditLogRepository::findAll)
                .orElse(emptyList());
    }

    @Override
    public List<AuditLog> findByEntityIds(Collection<String> entityIds) {
        return Optional.ofNullable(entityIds)
                .filter(CollectionUtils::isNotEmpty)
                .map(auditLogRepository::findAllByEntityIdIn)
                .orElse(emptyList());
    }

    @Override
    public Map<String, AuditLog> findGroupByEntityIds(Collection<String> entityIds) {
        return findByEntityIds(entityIds)
                .stream()
                .collect(toCaseInsensitiveMap(
                        AuditLog::getEntityId,
                        Function.identity()
                ));
    }
}
