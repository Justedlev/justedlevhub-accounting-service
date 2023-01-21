package com.justedlev.account.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String attribute;
    private SearchOperation operation;
    private Object value;
    @Builder.Default
    private boolean orPredicate = false;
}
