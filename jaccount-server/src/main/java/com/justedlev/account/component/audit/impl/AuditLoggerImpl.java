package com.justedlev.account.component.audit.impl;

import com.justedlev.account.common.AuditColumn;
import com.justedlev.account.component.audit.AuditLogFinder;
import com.justedlev.account.component.audit.AuditLogger;
import com.justedlev.account.repository.AuditLogRepository;
import com.justedlev.account.repository.entity.AuditLog;
import com.justedlev.account.repository.entity.Imprint;
import com.justedlev.account.util.AuditUtils;
import com.justedlev.common.entity.Auditable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditLoggerImpl implements AuditLogger {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogFinder auditLogFinder;

    @Override
    public Optional<AuditLog> audit(@NonNull Auditable auditable) {
        return auditAll(List.of(auditable))
                .stream()
                .findFirst();
    }

    @Override
    public List<AuditLog> auditAll(Collection<Auditable> auditableCollection) {
        var ids = getAuditableEntityIds(auditableCollection);
        var auditeMap = auditLogFinder.findLastGroupByEntityIds(ids);
        var auditLogs = auditableCollection.stream()
                .map(auditable -> buildAuditLog(auditable, auditeMap))
                .filter(Objects::nonNull)
                .toList();
        return auditLogRepository.saveAll(auditLogs);
    }

    private AuditLog buildAuditLog(Auditable auditable, Map<String, AuditLog> auditeMap) {
        return findEntityId(auditable)
                .filter(auditeMap::containsKey)
                .map(entityId -> {
                    var fields = AuditUtils.findAnnotatedFields(auditable, AuditColumn.class);
                    var lastAudit = auditeMap.get(entityId);
                    var imprints = fields.stream()
                            .map(field -> getImprint(auditable, field, lastAudit))
                            .collect(Collectors.toSet());
                    return AuditLog.builder()
                            .entityId(entityId)
                            .entityType(auditable.getClass().getName())
                            .imprints(imprints)
                            .build();
                })
                .orElse(null);
    }

    private Set<String> getAuditableEntityIds(Collection<Auditable> auditableCollection) {
        return auditableCollection.stream()
                .map(this::findEntityId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(() -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)));
    }

    private Optional<String> findEntityId(Auditable auditable) {
        return AuditUtils.findAnnotatedFields(auditable, Id.class)
                .stream()
                .findFirst()
                .flatMap(field -> AuditUtils.getFieldValue(auditable, field))
                .map(Object::toString);
    }

    private Imprint getImprint(Auditable auditable, Field field, AuditLog lastAudit) {
        var value = AuditUtils.getFieldValue(auditable, field)
                .map(Object::toString)
                .orElse(null);
        var oldValue = lastAudit.getImprints()
                .stream()
                .filter(imprint -> imprint.getFieldName().equalsIgnoreCase(field.getName()))
                .findFirst()
                .map(Imprint::getNewValue)
                .orElse(null);
        return Imprint.builder()
                .fieldName(field.getName())
                .fieldType(field.getType().getName())
                .newValue(value)
                .oldValue(oldValue)
                .build();
    }
}
