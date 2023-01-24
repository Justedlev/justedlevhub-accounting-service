package com.justedlev.account.repository.custom.impl;

import com.justedlev.account.repository.custom.AccountFilterableRepository;
import com.justedlev.account.repository.custom.filter.AccountFilter;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Account_;
import com.justedlev.account.util.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        applyFilter(cb, cq, root, filter);
        var query = em.createQuery(cq);

        if (ObjectUtils.isNotEmpty(filter.getPageable())) {
            var pageable = filter.getPageable();
            cq.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
            query.setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    private void applyFilter(CriteriaBuilder cb, CriteriaQuery<Account> cq,
                             Root<Account> root, @NonNull AccountFilter filter) {
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

        if (CollectionUtils.isNotEmpty(predicates)) {
            cq.where(predicates.toArray(Predicate[]::new));
        }
    }
}
