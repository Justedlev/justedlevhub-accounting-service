package com.justedlev.account.repository.specification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;

import javax.naming.OperationNotSupportedException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DynamicSpecification<E> implements Specification<E> {
    private final List<SearchCriteria> props;
    private static final String ERROR = "Operator %s not supported here";

    @Override
    public Predicate toPredicate(@NonNull Root<E> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        var and = props.stream()
                .filter(criteria -> !criteria.isOrPredicate())
                .map(current -> mapToPredicate(current, root, builder))
                .toArray(Predicate[]::new);
        var or = props.stream()
                .filter(SearchCriteria::isOrPredicate)
                .map(current -> mapToPredicate(current, root, builder))
                .toArray(Predicate[]::new);

        return builder.or(builder.and(and), builder.and(builder.or(or)));
    }

    @SneakyThrows
    private Predicate mapToPredicate(SearchCriteria criteria, Root<E> root, CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case EQUAL -> {
                return builder.equal(root.get(criteria.getAttribute()), criteria.getValue());
            }
            case GREATER_THAN, AFTER -> {
                return builder.greaterThan(root.get(criteria.getAttribute()), criteria.getValue().toString());
            }
            case LESS_THAN, BEFORE -> {
                return builder.lessThan(root.get(criteria.getAttribute()), criteria.getValue().toString());
            }
            case GREATER_THAN_OR_EQUAL -> {
                return builder.greaterThanOrEqualTo(root.get(criteria.getAttribute()), criteria.getValue().toString());
            }
            case LESS_THAN_OR_EQUAL -> {
                return builder.lessThanOrEqualTo(root.get(criteria.getAttribute()), criteria.getValue().toString());
            }
            case LIKE -> {
                return builder.like(root.get(criteria.getAttribute()), "%" + criteria.getValue() + "%");
            }
            case NOT_LIKE -> {
                return builder.notLike(root.get(criteria.getAttribute()), "%" + criteria.getValue() + "%");
            }
            case IN -> {
                return root.get(criteria.getAttribute()).in((Collection<?>) criteria.getValue());
            }
            case NOT_IN -> {
                return builder.not(root.get(criteria.getAttribute()).in((Collection<?>) criteria.getValue()));
            }
            case IS_NULL -> {
                return builder.isNull(root.get(criteria.getAttribute()));
            }
            case NOT_NULL -> {
                return builder.isNotNull(root.get(criteria.getAttribute()));
            }
            default -> throw new OperationNotSupportedException("Operation not supported yet");
        }
    }

    protected static <E> DynamicSpecificationBuilder<E> where(List<SearchCriteria> criteriaList) {
        return new DynamicSpecificationBuilder<>(criteriaList);
    }

    protected static <E> DynamicSpecificationBuilder<E> where(@NonNull SearchCriteria criteria) {
        var list = Stream.of(criteria)
                .collect(Collectors.toCollection(LinkedList::new));

        return where(list);
    }

    public static <E> DynamicSpecificationBuilder<E> where(@NonNull String attribute,
                                                           @NonNull SearchOperation operator) {
        return where(toCriteria(attribute, operator));
    }

    public static <E> DynamicSpecificationBuilder<E> where(@NonNull String attribute,
                                                           @NonNull SearchOperation operator,
                                                           @NonNull Object value) {
        return where(toCriteria(attribute, operator, value));
    }

    private static SearchCriteria toCriteria(String attribute, SearchOperation operator) {
        return switch (operator) {
            case IS_NULL, NOT_NULL -> SearchCriteria.builder()
                    .attribute(attribute)
                    .operation(operator)
                    .build();
            default -> throw new IllegalArgumentException(String.format(ERROR, operator));
        };
    }

    private static SearchCriteria toCriteria(String attribute, SearchOperation operator, Object value) {
        return switch (operator) {
            case NOT_NULL, IS_NULL -> throw new IllegalArgumentException(String.format(ERROR, operator));
            default -> SearchCriteria.builder()
                    .attribute(attribute)
                    .operation(operator)
                    .value(value)
                    .build();
        };
    }

    @RequiredArgsConstructor
    public static class DynamicSpecificationBuilder<E> {
        private final List<SearchCriteria> props;

        protected DynamicSpecificationBuilder<E> and(@NonNull SearchCriteria criteria) {
            props.add(criteria);

            return this;
        }

        public DynamicSpecificationBuilder<E> and(@NonNull String attribute,
                                                  @NonNull SearchOperation operator) {
            return this.and(toCriteria(attribute, operator));
        }

        public DynamicSpecificationBuilder<E> and(@NonNull String attribute,
                                                  @NonNull SearchOperation operator,
                                                  @NonNull Object value) {
            return this.and(toCriteria(attribute, operator, value));
        }

        protected DynamicSpecificationBuilder<E> or(@NonNull SearchCriteria criteria) {
            criteria.setOrPredicate(true);
            props.add(criteria);

            return this;
        }

        public DynamicSpecificationBuilder<E> or(@NonNull String attribute, @NonNull SearchOperation operator) {
            return this.or(toCriteria(attribute, operator));
        }

        public DynamicSpecificationBuilder<E> or(@NonNull String attribute,
                                                 @NonNull SearchOperation operator,
                                                 @NonNull Object value) {
            return this.or(toCriteria(attribute, operator, value));
        }

        public Specification<E> build() {
            if (props.isEmpty())
                return null;

            return new DynamicSpecification<>(this.props);
        }
    }
}
