package com.justedlev.account.repository.custom.impl;

import com.justedlev.account.repository.custom.AccountFilterableRepository;
import com.justedlev.account.repository.custom.filter.AccountFilter;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Account_;
import com.justedlev.account.util.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountFilterableRepositoryImpl implements AccountFilterableRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Account> findByFilter(@NonNull AccountFilter filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Account.class);
        var root = cq.from(Account.class);
        var predicates = buildPredicates(filter, cb, root);
        applyPredicates(cq, predicates);

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Page<Account> findByFilter(@NonNull AccountFilter filter, @NonNull Pageable pageable) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Account.class);
        var root = cq.from(Account.class);
        var predicates = buildPredicates(filter, cb, root);
        applyPredicates(cq, predicates);
        var query = em.createQuery(cq);
        applyPageable(pageable, cb, cq, root, query);
        var content = query.getResultList();

        return PageableExecutionUtils.getPage(content, pageable, () -> executeCountQuery(predicates));
    }

    private void applyPageable(Pageable pageable, CriteriaBuilder cb, CriteriaQuery<Account> cq,
                               Root<Account> root, TypedQuery<Account> query) {
        if (pageable.isPaged()) {
            cq.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
            query.setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize());
        }
    }

    private void applyPredicates(CriteriaQuery<Account> cq, Predicate... predicates) {
        if (ArrayUtils.isNotEmpty(predicates)) {
            cq.where(predicates);
        }
    }

    private long executeCountQuery(Predicate... predicates) {
        return em.createQuery(createCountQuery(predicates))
                .getSingleResult();
    }

    private CriteriaQuery<Long> createCountQuery(Predicate... predicates) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(Account.class);

        return cq.select(cb.count(root)).where(predicates);
    }

    private Predicate[] buildPredicates(AccountFilter filter, CriteriaBuilder cb, Root<Account> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getIds())) {
            predicates.add(root.get(Account_.id).in(filter.getIds()));
        }

        if (CollectionUtils.isNotEmpty(filter.getEmails())) {
            predicates.add(cb.lower(root.get(Account_.email)).in(Converter.toLowerCase(filter.getEmails())));
        }

        if (CollectionUtils.isNotEmpty(filter.getNicknames())) {
            predicates.add(cb.lower(root.get(Account_.nickname)).in(Converter.toLowerCase(filter.getNicknames())));
        }

        if (CollectionUtils.isNotEmpty(filter.getModes())) {
            predicates.add(root.get(Account_.mode).in(filter.getModes()));
        }

        if (ObjectUtils.isNotEmpty(filter.getModeAtFrom())) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Account_.modeAt), filter.getModeAtFrom()));
        }

        if (ObjectUtils.isNotEmpty(filter.getModeAtTo())) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Account_.modeAt), filter.getModeAtTo()));
        }

        if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
            predicates.add(root.get(Account_.status).in(filter.getStatuses()));
        }

        if (CollectionUtils.isNotEmpty(filter.getActivationCodes())) {
            predicates.add(root.get(Account_.activationCode).in(filter.getActivationCodes()));
        }

        return predicates.toArray(Predicate[]::new);
    }
}
