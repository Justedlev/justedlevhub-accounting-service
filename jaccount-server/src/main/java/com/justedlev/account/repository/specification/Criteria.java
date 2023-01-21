package com.justedlev.account.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
class Criteria {
    private String attribute;
    private ComparisonOperator operator;
    private Object first;
    private Object second;
    @Builder.Default
    private boolean orPredicate = false;
}
