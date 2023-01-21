package com.justedlev.account.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String field;
    private QueryOperator operation;
    private Object value;
}
