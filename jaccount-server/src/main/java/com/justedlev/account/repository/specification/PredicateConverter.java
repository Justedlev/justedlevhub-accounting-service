package com.justedlev.account.repository.specification;

import lombok.NonNull;

import javax.naming.OperationNotSupportedException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class PredicateConverter {

    private PredicateConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static <E> Predicate toPredicate(@NonNull Filter criteria, @NonNull Root<E> root,
                                            @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder)
            throws OperationNotSupportedException {
        switch (criteria.getOperation()) {
            case EQUAL -> {
                return builder.equal(root.get(criteria.getField()), criteria.getValue());
            }
            case GREATER_THAN, AFTER -> {
                return builder.greaterThan(root.get(criteria.getField()), criteria.getValue().toString());
            }
            case LESS_THAN, BEFORE -> {
                return builder.lessThan(root.get(criteria.getField()), criteria.getValue().toString());
            }
            case GREATER_THAN_OR_EQUAL -> {
                return builder.greaterThanOrEqualTo(root.get(criteria.getField()), criteria.getValue().toString());
            }
            case LESS_THAN_OR_EQUAL -> {
                return builder.lessThanOrEqualTo(root.get(criteria.getField()), criteria.getValue().toString());
            }
            case LIKE -> {
                return builder.like(builder.lower(root.get(criteria.getField())), ("%" + criteria.getValue() + "%").toLowerCase());
            }
            case NOT_LIKE -> {
                return builder.notLike(builder.lower(root.get(criteria.getField())), ("%" + criteria.getValue() + "%").toLowerCase());
            }
            case IN -> {
                return builder.in(root.get(criteria.getField())).value(criteria.getValue());
            }
            case NOT_IN -> {
                return builder.not(builder.in(root.get(criteria.getField())).value(criteria.getValue()));
            }
            case IS_NULL -> {
                return builder.isNull(root.get(criteria.getField()));
            }
            case NOT_NULL -> {
                return builder.isNotNull(root.get(criteria.getField()));
            }
            default -> throw new OperationNotSupportedException("Operation not supported yet");
        }
    }
}
