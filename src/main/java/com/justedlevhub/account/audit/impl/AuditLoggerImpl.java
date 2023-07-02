package com.justedlevhub.account.audit.impl;

import com.justedlevhub.account.audit.AuditColumn;
import com.justedlevhub.account.audit.AuditLogFinder;
import com.justedlevhub.account.audit.AuditLogger;
import com.justedlevhub.account.audit.repository.AuditLogRepository;
import com.justedlevhub.account.audit.repository.entity.AuditLog;
import com.justedlevhub.account.audit.repository.entity.AuditSnapshot;
import com.justedlevhub.account.audit.repository.entity.base.Auditable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.justedlevhub.account.util.CustomCollectors.toCaseInsensitiveSet;
import static java.util.Comparator.comparing;
import static java.util.function.BinaryOperator.maxBy;
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

    @Transactional
    @Override
    public Optional<AuditLog> audit(@NonNull Auditable auditable) {
        return auditAll(List.of(auditable))
                .stream()
                .findFirst();
    }

    @Transactional
    @Override
    public List<AuditLog> auditAll(Collection<Auditable> auditableCollection) {
        var ids = getEntityIds(auditableCollection);
        var auditesMap = auditLogFinder.findGroupByEntityIds(ids);
        var auditLogs = auditableCollection.stream()
                .map(auditable -> buildAuditLog(auditable, auditesMap))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(auditLog -> CollectionUtils.isNotEmpty(auditLog.getSnapshots()))
                .toList();

        return auditLogRepository.saveAll(auditLogs);
    }

    private Optional<AuditLog> buildAuditLog(Auditable auditable, Map<String, AuditLog> auditesMap) {
        return findEntityId(auditable).map(entityId -> toAuditLog(auditable, auditesMap, entityId));
    }

    @SuppressWarnings("unchecked")
    private AuditLog toAuditLog(Auditable auditable, Map<String, AuditLog> auditesMap, String entityId) {
        var fields = getFields(auditable.getClass(), withAnnotation(AuditColumn.class));
        var auditLog = Optional.ofNullable(entityId)
                .map(auditesMap::get)
                .orElseGet(() -> AuditLog.builder()
                        .entityId(entityId)
                        .entityType(auditable.getClass().getName())
                        .snapshots(Collections.emptyList())
                        .build());
        var oldValueMap = auditLog.getSnapshots()
                .stream()
                .collect(Collectors.toMap(
                        AuditSnapshot::getFieldName,
                        Function.identity(),
                        maxBy(comparing(AuditSnapshot::getCreatedAt))
                ));
        var snapshots = getSnapshots(auditable, fields, oldValueMap);
        auditLog.setSnapshots(snapshots);
        snapshots.forEach(snapshot -> snapshot.setAuditLog(auditLog));

        return auditLog;
    }

    private List<AuditSnapshot> getSnapshots(Auditable auditable, Set<Field> fields, Map<String, AuditSnapshot> oldValueMap) {
        return fields.stream()
                .map(field -> getNewSnapshot(auditable, field, oldValueMap))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Set<String> getEntityIds(Collection<Auditable> auditableCollection) {
        return auditableCollection.stream()
                .map(this::findEntityId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toCaseInsensitiveSet());
    }

    @SuppressWarnings("unchecked")
    private Optional<String> findEntityId(Auditable auditable) {
        var type = auditable.getClass();

        return getFields(type, withAnnotation(Id.class).or(withAnnotation(EmbeddedId.class)))
                .stream()
                .findFirst()
                .map(field -> getFieldValue(auditable, field));
    }

    private Optional<AuditSnapshot> getNewSnapshot(Auditable auditable, Field field, Map<String, AuditSnapshot> oldValueMap) {
        var newValue = getFieldValue(auditable, field);
        var oldValue = Optional.of(field.getName())
                .map(oldValueMap::get)
                .map(AuditSnapshot::getNewValue)
                .orElse(null);
        if (Objects.equals(newValue, oldValue)) return Optional.empty();
        var auditColumn = field.getAnnotation(AuditColumn.class);
        var res = AuditSnapshot.builder()
                .fieldType(field.getType().getName())
                .fieldName(field.getName())
                .newValue(newValue)
                .previousValue(oldValue)
                .hide(auditColumn.hide())
                .build();

        return Optional.of(res);
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
