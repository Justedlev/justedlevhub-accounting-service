package com.justedlev.account.common.jaudit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justedlev.account.repository.JAuditRepository;
import com.justedlev.account.repository.entity.JAudit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class JAuditAspect {
    @PersistenceContext
    private final EntityManager em;
    private final JAuditRepository jAuditRepository;
    private final ObjectMapper objectMapper;

    @Pointcut("execution(* org.springframework.data.jpa.repository.*+.save*(*))")
    public void savePointcut() {
    }

    @Before("savePointcut()")
    public void beforeSave(JoinPoint jp) {
        JAuditUtils.mapToEntity(jp)
                .map(List::of)
                .ifPresent(this::handleAll);
    }

    @Pointcut("execution(* org.springframework.data.jpa.repository.*+.saveAll*(*))")
    public void saveAllPointcut() {
    }

    @Before("saveAllPointcut()")
    public void beforeSaveAll(JoinPoint jp) {
        handleAll(JAuditUtils.mapToEntities(jp));
    }

    private void handleAll(Collection<?> entities) {
        entities.forEach(em::detach);
        var type = entities.stream()
                .map(Object::getClass)
                .findFirst()
                .orElse(null);
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(type);
        var result = em.createQuery(cq).getResultList();
        var auditList = result.stream()
                .map(this::mapToJaudit)
                .toList();
        var res = jAuditRepository.saveAll(auditList);
        log.info("Created {} audit objects", res.size());
    }

    @SneakyThrows
    private JAudit mapToJaudit(Object entity) {
        return JAudit.builder()
                .entityType(entity.getClass())
                .payload(objectMapper.writeValueAsString(entity))
                .build();
    }
}
