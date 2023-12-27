package com.justedlev.common.activity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityRegistrarRepositoryImpl<R extends Serializable, ID extends Serializable>
        implements ActivityRegistrarRepository<R> {
    @PersistenceContext
    private final EntityManager em;
    private final JpaEntityInformation<Activity, ID> entityInformation;

    @Override
    public Optional<Activity> findLastById(R id) {
        return Optional.ofNullable(em.createQuery(buildIdCriteriaQuery(id)).getSingleResult());
    }

    private CriteriaQuery<Activity> buildIdCriteriaQuery(R id) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(entityInformation.getJavaType());
        var root = cq.from(entityInformation.getJavaType());
        cq.select(root);

        return cq.where(cb.equal(root.get(Activity_.entityId), id));
    }

    @Override
    public List<Activity> findAllById(R id) {
        return em.createQuery(buildIdCriteriaQuery(id)).getResultList();
    }

    @Override
    public Page<Activity> findAllById(R id, @NonNull Pageable pageable) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(entityInformation.getJavaType());
        var root = cq.from(entityInformation.getJavaType());
        cq.select(root);
        cq.where(cb.equal(root.get(Activity_.entityId), id));

        if (pageable.getSort().isSorted()) {
            cq.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
        }

        var res = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return PageableExecutionUtils.getPage(res, pageable,
                () -> getCountQuery(id, root).getSingleResult());
    }

    private TypedQuery<Long> getCountQuery(R id, Root<Activity> root) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var predicate = cb.equal(root.get(Activity_.entityId), id);
        cq.select(cb.count(predicate));

        return em.createQuery(cq);
    }
}
