package com.justedlev.account.repository.custom.impl;

import com.justedlev.account.repository.custom.ContactFilterableRepository;
import com.justedlev.account.repository.custom.filter.ContactFilter;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Account_;
import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.entity.Contact_;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContactFilterableRepositoryImpl implements ContactFilterableRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Contact> findByFilter(ContactFilter filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Contact.class);
        var root = cq.from(Contact.class);
        var accountJoin = (Join<Contact, Account>) root.fetch(Contact_.account, JoinType.LEFT);
        var contactPredicates = filter.getAccountFilter().build(cb, accountJoin);
        var predicates = filter.build(cb, root);
        predicates.addAll(contactPredicates);
        createSearchPredicate(filter.getSearchText(), cb, root, accountJoin)
                .ifPresent(predicates::add);
        filter.apply(cq, predicates);

        return em.createQuery(cq).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Contact> findByFilter(ContactFilter filter, Pageable pageable) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Contact.class);
        var root = cq.from(Contact.class);
        var accountJoin = (Join<Contact, Account>) root.fetch(Contact_.account, JoinType.LEFT);
        var contactPredicates = filter.getAccountFilter().build(cb, accountJoin);
        var predicates = filter.build(cb, root);
        predicates.addAll(contactPredicates);
        createSearchPredicate(filter.getSearchText(), cb, root, accountJoin)
                .ifPresent(predicates::add);
        var predicateArray = filter.apply(cq, predicates);
        var content = applyPageable(pageable, cb, cq, root).getResultList();

        return PageableExecutionUtils.getPage(content, pageable, () -> executeCountQuery(predicateArray));
    }

    private TypedQuery<Contact> applyPageable(Pageable pageable, CriteriaBuilder cb,
                                              CriteriaQuery<Contact> cq, Root<Contact> root) {
        Optional.ofNullable(pageable)
                .map(Pageable::getSort)
                .filter(Sort::isSorted)
                .map(current -> QueryUtils.toOrders(pageable.getSort(), root, cb))
                .ifPresent(cq::orderBy);
        var query = em.createQuery(cq);
        Optional.ofNullable(pageable)
                .filter(Pageable::isPaged)
                .ifPresent(current -> query.setFirstResult((int) current.getOffset())
                        .setMaxResults(current.getPageSize()));

        return query;
    }

    private long executeCountQuery(Predicate[] predicates) {
        return em.createQuery(createCountQuery(predicates))
                .getSingleResult();
    }

    private CriteriaQuery<Long> createCountQuery(Predicate[] predicates) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(Contact.class);
        root.join(Contact_.account, JoinType.LEFT);
        Optional.ofNullable(predicates)
                .filter(ArrayUtils::isNotEmpty)
                .ifPresent(cq::where);

        return cq.select(cb.count(root));
    }

    private Optional<Predicate> createSearchPredicate(String searchText,
                                                      CriteriaBuilder cb,
                                                      Path<Contact> root,
                                                      Join<Contact, Account> accountJoin) {
        return Optional.ofNullable(searchText)
                .filter(StringUtils::isNotBlank)
                .map(String::toLowerCase)
                .map(String::strip)
                .map(q -> q.replaceAll("\\s+", " "))
                .map(q -> "%" + q + "%")
                .map(q -> cb.or(
                        cb.like(cb.lower(accountJoin.get(Account_.nickname)), q),
                        cb.like(cb.lower(accountJoin.get(Account_.firstName)), q),
                        cb.like(cb.lower(accountJoin.get(Account_.lastName)), q),
                        cb.like(cb.lower(root.get(Contact_.email)), q),
                        cb.like(cb.lower(root.get(Contact_.phoneNumber)), q)
                ));
    }
}
