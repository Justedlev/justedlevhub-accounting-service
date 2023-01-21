package com.justedlev.account.repository.specification;

import lombok.NonNull;

import javax.naming.OperationNotSupportedException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class PredicateConverter {

    private PredicateConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static <E> Predicate toPredicate(@NonNull Root<E> root, @NonNull SearchCriteria criteria,
                                            @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder)
            throws OperationNotSupportedException {
        String attributeName = criteria.getAttribute();
        Object value = criteria.getValue();
        switch (criteria.getOperation()) {
            case EQUAL -> {
                return builder.equal(root.get(attributeName), value);
            }
            case GREATER_THAN, AFTER -> {
                return builder.greaterThan(root.get(attributeName), value.toString());
            }
            case LESS_THAN, BEFORE -> {
                return builder.lessThan(root.get(attributeName), value.toString());
            }
            case GREATER_THAN_OR_EQUAL -> {
                return builder.greaterThanOrEqualTo(root.get(attributeName), value.toString());
            }
            case LESS_THAN_OR_EQUAL -> {
                return builder.lessThanOrEqualTo(root.get(attributeName), value.toString());
            }
            case LIKE -> {
                return builder.like(root.get(attributeName), "%" + value.toString() + "%");
            }
            case NOT_LIKE -> {
                return builder.notLike(root.get(attributeName), "%" + value.toString() + "%");
            }
            case IN -> {
                if (value instanceof Collection) {
                    return root.get(attributeName).in((Collection<?>) value);
                } else {
                    return builder.like(root.get(attributeName), value.toString());
                }
            }
            case NOT_IN -> {
                if (value instanceof Collection) {
                    return builder.not(root.get(attributeName).in((Collection<?>) value));
                } else {
                    return builder.notLike(root.get(attributeName), value.toString());
                }
            }
            case IS_NULL -> {
                return builder.isNull(root.get(attributeName));
            }
            case NOT_NULL -> {
                return builder.isNotNull(root.get(attributeName));
            }
            default -> throw new OperationNotSupportedException("Operation not supported yet");
        }
    }
}
