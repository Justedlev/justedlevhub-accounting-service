package com.justedlev.account.component.base;

import java.util.List;

public interface EntityMapper<I, O> {
    O mapToEntity(I request);

    List<O> mapToEntity(List<I> requests);
}
