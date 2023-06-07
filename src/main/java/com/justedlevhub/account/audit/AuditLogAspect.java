package com.justedlevhub.account.audit;

import com.justedlevhub.account.audit.repository.entity.AuditLog;
import com.justedlevhub.account.audit.repository.entity.base.Auditable;
import com.justedlevhub.account.util.AspectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {
    private final AuditLogger auditLogger;

    @Pointcut("execution(* com.justedlevhub.account.repository.*.save*(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* com.justedlevhub.account.repository.*+.saveAll*(..))")
    public void saveAllPointcut() {
    }

    @Async
    @After("savePointcut()")
    public void savePointcutHandler(JoinPoint joinPoint) {
        AspectUtils.mapToEntity(joinPoint, Auditable.class)
                .flatMap(auditLogger::audit)
                .ifPresent(this::logAuditCreated);
    }

    private void logAuditCreated(AuditLog auditLog) {
        log.info("Audit was created for entity: {}", auditLog.getEntityType());
    }

    @Async
    @After("saveAllPointcut()")
    public void saveAllPointcutHandler(JoinPoint joinPoint) {
        var autitableCollection = AspectUtils.mapToEntities(joinPoint, Auditable.class);
        var audits = auditLogger.auditAll(autitableCollection);
        logAuditsCreated(audits);
    }

    private void logAuditsCreated(List<AuditLog> audits) {
        var entityType = audits.stream()
                .map(AuditLog::getEntityType)
                .findFirst()
                .orElse("Undefined entity type");
        log.info("{} audit logs was created for entity: {}", audits.size(), entityType);
    }
}
