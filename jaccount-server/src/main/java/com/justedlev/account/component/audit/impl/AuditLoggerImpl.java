package com.justedlev.account.component.audit.impl;

import com.justedlev.account.common.AuditColumn;
import com.justedlev.account.component.audit.AuditLogFinder;
import com.justedlev.account.component.audit.AuditLogger;
import com.justedlev.account.repository.AuditLogRepository;
import com.justedlev.account.repository.entity.AuditLog;
import com.justedlev.account.repository.entity.Imprint;
import com.justedlev.common.entity.Auditable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.json.Id;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.getMethods;
import static org.reflections.util.ReflectionUtilsPredicates.withAnnotation;
import static org.reflections.util.ReflectionUtilsPredicates.withName;
import static org.springframework.util.ReflectionUtils.invokeMethod;

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
                .map(entityId -> toAuditLog(auditable, auditeMap, entityId))
                .orElse(null);
    }

    private AuditLog toAuditLog(Auditable auditable, Map<String, AuditLog> auditeMap, String entityId) {
        var fields = findAnnotatedFields(auditable, AuditColumn.class);
        var entityType = auditable.getClass().getSimpleName();
        var lastAudit = auditeMap.get(entityId);
        var imprints = getImprints(auditable, fields, lastAudit);

        return AuditLog.builder()
                .entityId(entityId)
                .entityType(entityType)
                .imprints(imprints)
                .build();
    }

    private Set<Imprint> getImprints(Auditable auditable, Set<Field> fields, AuditLog lastAudit) {
        return fields.stream()
                .map(field -> getImprint(auditable, field, lastAudit))
                .collect(Collectors.toSet());
    }

    private Set<String> getAuditableEntityIds(Collection<Auditable> auditableCollection) {
        return auditableCollection.stream()
                .map(this::findEntityId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(() -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)));
    }

    private Optional<String> findEntityId(Auditable auditable) {
        return findAnnotatedFields(auditable, Id.class)
                .stream()
                .findFirst()
                .flatMap(field -> getFieldValue(auditable, field))
                .map(Object::toString);
    }

    private Imprint getImprint(Auditable auditable, Field field, AuditLog lastAudit) {
        var value = getFieldValue(auditable, field)
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

    @SuppressWarnings("unchecked")
    public Set<Field> findAnnotatedFields(Auditable auditable, Class<? extends Annotation> annotation) {
        return getFields(auditable.getClass(), withAnnotation(annotation));
    }

    @SuppressWarnings("unchecked")
    public Optional<Method> findGetMethod(Auditable auditable, Field field) {
        var methodGet = "get" + StringUtils.capitalize(field.getName());

        return getMethods(auditable.getClass(), withName(methodGet))
                .stream()
                .findFirst();
    }

    public Optional<Object> getFieldValue(Auditable auditable, Field field) {
        return findGetMethod(auditable, field)
                .map(method -> invokeMethod(method, auditable));
    }
}
