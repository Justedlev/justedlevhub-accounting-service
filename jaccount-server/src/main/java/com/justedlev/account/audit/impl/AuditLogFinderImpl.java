package com.justedlev.account.audit.impl;

import com.justedlev.account.audit.AuditLogFinder;
import com.justedlev.account.audit.repository.AuditLogRepository;
import com.justedlev.account.audit.repository.entity.AuditLog;
import com.justedlev.account.audit.repository.specification.AuditLogSpecification;
import com.justedlev.account.audit.repository.specification.filter.AuditLogFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuditLogFinderImpl implements AuditLogFinder {
    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditLog> findByFilter(AuditLogFilter filter) {
        return Optional.ofNullable(filter)
                .map(AuditLogSpecification::from)
                .map(auditLogRepository::findAll)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<AuditLog> findByEntityIds(Collection<String> entityIds) {
        return Optional.ofNullable(entityIds)
                .filter(CollectionUtils::isNotEmpty)
                .map(auditLogRepository::findAllByEntityIdIn)
                .orElse(Collections.emptyList());
    }

    @Override
    public Map<String, List<AuditLog>> findGroupByEntityIds(Collection<String> entityIds) {
        return findByEntityIds(entityIds)
                .stream()
                .collect(Collectors.groupingBy(
                        AuditLog::getEntityId,
                        () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                        Collectors.toList()
                ));
    }

    @Override
    public Map<String, AuditLog> findLastGroupByEntityIds(Collection<String> entityIds) {
        return findByEntityIds(entityIds)
                .stream()
                .collect(Collectors.toMap(
                        AuditLog::getEntityId,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(AuditLog::getCreatedAt)),
                        () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)
                ));
    }
}
