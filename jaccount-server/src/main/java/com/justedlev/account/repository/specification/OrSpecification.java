package com.justedlev.account.repository.specification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OrSpecification<E> implements Specification<E> {
    private final List<Filter> props;
    private static final String ERROR = "Operator %s not supported here";

    @Override
    public Predicate toPredicate(@NonNull Root<E> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        var predicates = props.stream()
                .map(current -> mapToPredicate(current, root, query, builder))
                .toArray(Predicate[]::new);

        return builder.or(predicates);
    }

    @SneakyThrows
    private Predicate mapToPredicate(Filter criteria,
                                     @NonNull Root<E> root,
                                     @NonNull CriteriaQuery<?> query,
                                     @NonNull CriteriaBuilder builder) {
        return PredicateConverter.toPredicate(criteria, root, query, builder);
    }

    protected static <E> OrSpecificationBuilder<E> where(List<Filter> criteriaList) {
        return new OrSpecificationBuilder<>(criteriaList);
    }

    protected static <E> OrSpecificationBuilder<E> where(@NonNull Filter criteria) {
        var list = Stream.of(criteria)
                .collect(Collectors.toCollection(LinkedList::new));

        return where(list);
    }

    public static <E> OrSpecificationBuilder<E> where(@NonNull String attribute,
                                                      @NonNull QueryOperator operator) {
        return where(toCriteria(attribute, operator));
    }

    public static <E> OrSpecificationBuilder<E> where(@NonNull String attribute,
                                                      @NonNull QueryOperator operator,
                                                      @NonNull Object value) {
        return where(toCriteria(attribute, operator, value));
    }

    private static Filter toCriteria(String attribute, QueryOperator operator) {
        return switch (operator) {
            case IS_NULL, NOT_NULL -> Filter.builder()
                    .field(attribute)
                    .operation(operator)
                    .build();
            default -> throw new IllegalArgumentException(String.format(ERROR, operator));
        };
    }

    private static Filter toCriteria(String attribute, QueryOperator operator, Object value) {
        return switch (operator) {
            case NOT_NULL, IS_NULL -> throw new IllegalArgumentException(String.format(ERROR, operator));
            default -> Filter.builder()
                    .field(attribute)
                    .operation(operator)
                    .value(value)
                    .build();
        };
    }

    @RequiredArgsConstructor
    public static class OrSpecificationBuilder<E> {
        private final List<Filter> props;

        protected OrSpecificationBuilder<E> or(@NonNull Filter criteria) {
            props.add(criteria);

            return this;
        }

        public OrSpecificationBuilder<E> or(@NonNull String attribute, @NonNull QueryOperator operator) {
            return this.or(toCriteria(attribute, operator));
        }

        public OrSpecificationBuilder<E> or(@NonNull String attribute,
                                            @NonNull QueryOperator operator,
                                            @NonNull Object value) {
            return this.or(toCriteria(attribute, operator, value));
        }

        public Specification<E> build() {
            if (props.isEmpty())
                return null;

            return new OrSpecification<>(this.props);
        }
    }
}
