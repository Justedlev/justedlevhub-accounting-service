package com.justedlev.account.repository.specification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SpecificationBuilder<E> {
    private final List<Criteria> criteriaList;
    private static final String ERROR = "Operator %s not supported here";

    protected static <E> SpecificationBuilder<E> where(List<Criteria> criteriaList) {
        return new SpecificationBuilder<>(criteriaList);
    }

    protected static <E> SpecificationBuilder<E> where(@NonNull Criteria criteria) {
        var list = Stream.of(criteria)
                .collect(Collectors.toCollection(LinkedList::new));

        return where(list);
    }

    public static <E> SpecificationBuilder<E> where(@NonNull String attribute,
                                                    @NonNull ComparisonOperator operator) {
        return where(toCriteria(attribute, operator));
    }

    public static <E> SpecificationBuilder<E> where(@NonNull String attribute,
                                                    @NonNull ComparisonOperator operator,
                                                    @NonNull Object value) {
        return where(toCriteria(attribute, operator, value));
    }

    public static <E> SpecificationBuilder<E> where(@NonNull String attribute,
                                                    @NonNull ComparisonOperator operator,
                                                    @NonNull Object value1, @NonNull Object value2) {
        return where(toCriteria(attribute, operator, value1, value2));
    }

    protected SpecificationBuilder<E> and(@NonNull Criteria criteria) {
        criteriaList.add(criteria);

        return this;
    }

    public SpecificationBuilder<E> and(@NonNull String attribute,
                                       @NonNull ComparisonOperator operator) {
        return this.and(toCriteria(attribute, operator));
    }

    public SpecificationBuilder<E> and(@NonNull String attribute,
                                       @NonNull ComparisonOperator operator,
                                       @NonNull Object value1, @NonNull Object value2) {
        return this.and(toCriteria(attribute, operator, value1, value2));
    }

    public SpecificationBuilder<E> and(@NonNull String attribute,
                                       @NonNull ComparisonOperator operator,
                                       @NonNull Object value) {
        return this.and(toCriteria(attribute, operator, value));
    }

    protected SpecificationBuilder<E> or(@NonNull Criteria criteria) {
        criteria.setOrPredicate(true);
        criteriaList.add(criteria);

        return this;
    }

    public SpecificationBuilder<E> or(@NonNull String attribute, @NonNull ComparisonOperator operator) {
        return this.or(toCriteria(attribute, operator));
    }

    public SpecificationBuilder<E> or(@NonNull String attribute,
                                      @NonNull ComparisonOperator operator,
                                      @NonNull Object value1, @NonNull Object value2) {
        return this.or(toCriteria(attribute, operator, value1, value2));
    }

    public SpecificationBuilder<E> or(String attribute, ComparisonOperator operator, Object value) {
        return this.or(toCriteria(attribute, operator, value));
    }

    public Specification<E> build() {
        if (criteriaList.isEmpty())
            return null;

        Specification<E> result = new SpecificationImpl<>(criteriaList.get(0));

        for (int i = 1; i < criteriaList.size(); i++) {
            result = criteriaList.get(i).isOrPredicate()
                    ? Specification.where(result).or(new SpecificationImpl<>(criteriaList.get(i)))
                    : Specification.where(result).and(new SpecificationImpl<>(criteriaList.get(i)));
        }

        return result;
    }

    private static Criteria toCriteria(String attribute, ComparisonOperator operator) {
        return switch (operator) {
            case IS_NULL, NOT_NULL -> Criteria.builder()
                    .attribute(attribute)
                    .operator(operator)
                    .build();
            default -> throw new IllegalArgumentException(String.format(ERROR, operator));
        };
    }

    private static Criteria toCriteria(String attribute, ComparisonOperator operator, Object value1) {
        return switch (operator) {
            case BETWEEN, NOT_NULL, IS_NULL -> throw new IllegalArgumentException(String.format(ERROR, operator));
            default -> Criteria.builder()
                    .attribute(attribute)
                    .operator(operator)
                    .first(value1)
                    .build();
        };
    }

    private static Criteria toCriteria(String attribute, ComparisonOperator operator, Object value1, Object value2) {
        if (!ComparisonOperator.BETWEEN.equals(operator))
            throw new IllegalArgumentException(String.format(ERROR, operator));

        return Criteria.builder()
                .attribute(attribute)
                .operator(operator)
                .first(value1)
                .second(value2)
                .build();
    }
}
