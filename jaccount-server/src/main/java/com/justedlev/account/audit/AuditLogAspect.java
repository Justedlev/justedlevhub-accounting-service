package com.justedlev.account.audit;

import com.justedlev.account.util.AspectUtils;
import com.justedlev.common.entity.Auditable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {
    private final AuditLogger auditLogger;

    @Pointcut("execution(* com.justedlev.account.repository.*.save*(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* com.justedlev.account.repository.*+.saveAll*(..))")
    public void saveAllPointcut() {
    }

    @Async
    @After("savePointcut()")
    public void savePointcutHandler(JoinPoint joinPoint) {
        AspectUtils.mapToEntity(joinPoint, Auditable.class)
                .ifPresent(auditable -> auditLogger.audit(auditable)
                        .ifPresent(auditLog ->
                                log.info("Audit was created for: {}", auditable.getClass().getName())));
    }

    @Async
    @After("saveAllPointcut()")
    public void saveAllPointcutHandler(JoinPoint joinPoint) {
        var autitableCollection = AspectUtils.mapToEntities(joinPoint, Auditable.class);
        var audits = auditLogger.auditAll(autitableCollection);
        log.info("{} audit logs was created for: {}", audits.size(), autitableCollection.getClass());
    }
}