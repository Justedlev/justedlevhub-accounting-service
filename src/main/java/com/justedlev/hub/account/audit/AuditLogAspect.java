package com.justedlev.hub.account.audit;

import com.justedlev.hub.account.audit.repository.entity.AuditLog;
import com.justedlev.hub.account.audit.repository.entity.base.Auditable;
import com.justedlev.hub.account.util.AspectUtils;
import com.justedlev.hub.account.util.CustomCollectors;
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

    @Pointcut("execution(* org.springframework.data.jpa.repository.*+.save*(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* org.springframework.data.jpa.repository.*+.saveAll*(..))")
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
        var entityTypes = audits.stream()
                .map(AuditLog::getEntityType)
                .collect(CustomCollectors.toCaseInsensitiveSet());
        log.info("{} audit logs was created for entity: {}", audits.size(), entityTypes);
    }
}
