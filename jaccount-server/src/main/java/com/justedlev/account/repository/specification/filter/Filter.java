package com.justedlev.account.repository.specification.filter;

import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public interface Filter<E> {
    List<Predicate> build(CriteriaBuilder cb, Path<E> path);

    default Predicate[] apply(CriteriaBuilder cb, CriteriaQuery<E> cq, Path<E> path) {
        return apply(cq, build(cb, path));
    }

    default Predicate[] apply(CriteriaQuery<E> cq, List<Predicate> predicates) {
        return Optional.ofNullable(predicates)
                .filter(CollectionUtils::isNotEmpty)
                .map(current -> {
                    var array = current.toArray(Predicate[]::new);
                    cq.where(array);

                    return array;
                })
                .orElse(new Predicate[0]);
    }
}
