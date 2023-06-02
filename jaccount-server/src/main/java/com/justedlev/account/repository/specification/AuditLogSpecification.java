package com.justedlev.account.repository.specification;

import com.justedlev.account.repository.entity.AuditLog;
import com.justedlev.account.repository.entity.AuditLog_;
import com.justedlev.account.repository.specification.filter.AuditLogFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditLogSpecification implements Specification<AuditLog> {
    private final AuditLogFilter filter;

    public static AuditLogSpecification from(AuditLogFilter filter) {
        return new AuditLogSpecification(filter);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<AuditLog> root,
                                 @NonNull CriteriaQuery<?> cq,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getIds())) {
            predicates.add(root.get(AuditLog_.id).in(filter.getIds()));
        }

        if (CollectionUtils.isNotEmpty(filter.getEntityIds())) {
            predicates.add(root.get(AuditLog_.entityId).in(filter.getEntityIds()));
        }

        if (CollectionUtils.isNotEmpty(filter.getEntityTypes())) {
            predicates.add(root.get(AuditLog_.entityType).in(filter.getEntityTypes()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
