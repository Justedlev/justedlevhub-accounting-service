package com.justedlevhub.account.audit.impl;

import com.justedlevhub.account.audit.AuditColumn;
import com.justedlevhub.account.audit.AuditLogFinder;
import com.justedlevhub.account.audit.AuditLogger;
import com.justedlevhub.account.audit.repository.AuditLogRepository;
import com.justedlevhub.account.audit.repository.entity.AuditImprint;
import com.justedlevhub.account.audit.repository.entity.AuditLog;
import com.justedlevhub.account.audit.repository.entity.base.Auditable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
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
        var ids = getEntityIds(auditableCollection);
        var auditeMap = auditLogFinder.findLastGroupByEntityIds(ids);
        var auditLogs = auditableCollection.stream()
                .map(auditable -> buildAuditLog(auditable, auditeMap))
                .filter(Objects::nonNull)
                .toList();

        return auditLogRepository.saveAll(auditLogs);
    }

    private AuditLog buildAuditLog(Auditable auditable, Map<String, AuditLog> auditeMap) {
        return findEntityId(auditable)
                .map(entityId -> toAuditLog(auditable, auditeMap, entityId))
                .orElse(null);
    }

    private AuditLog toAuditLog(Auditable auditable, Map<String, AuditLog> auditeMap, String entityId) {
        var fields = findAnnotatedFields(auditable, AuditColumn.class);
        var entityType = auditable.getClass().getSimpleName();
        var lastImprints = Optional.ofNullable(entityId)
                .map(auditeMap::get)
                .map(AuditLog::getImprints)
                .orElse(Collections.emptyList());
        var oldValueMap = lastImprints.stream()
                .filter(auditImprint -> Objects.nonNull(auditImprint.getNewValue()))
                .collect(Collectors.toMap(AuditImprint::getFieldName, AuditImprint::getNewValue));
        var imprints = getImprints(auditable, fields, oldValueMap);

        if (CollectionUtils.isEmpty(imprints)) return null;

        return AuditLog.builder()
                .entityId(entityId)
                .entityType(entityType)
                .imprints(imprints)
                .build();
    }

    private Set<AuditImprint> getImprints(Auditable auditable, Set<Field> fields, Map<String, String> oldValueMap) {
        return fields.stream()
                .map(field -> getImprint(auditable, field, oldValueMap))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<String> getEntityIds(Collection<Auditable> auditableCollection) {
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
                .map(field -> getFieldValue(auditable, field));
    }

    private AuditImprint getImprint(Auditable auditable, Field field, Map<String, String> oldValueMap) {
        var newValue = getFieldValue(auditable, field);
        var oldValue = oldValueMap.get(field.getName());

        if (Objects.equals(newValue, oldValue)) return null;

        var auditColumn = field.getAnnotation(AuditColumn.class);

        return AuditImprint.builder()
                .fieldType(field.getType())
                .fieldName(field.getName())
                .newValue(newValue)
                .oldValue(oldValue)
                .hide(auditColumn.hide())
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

    public String getFieldValue(Auditable auditable, Field field) {
        return findGetMethod(auditable, field)
                .map(method -> invokeMethod(method, auditable))
                .map(Object::toString)
                .orElse(null);
    }
}