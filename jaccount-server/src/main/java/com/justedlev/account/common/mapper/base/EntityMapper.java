package com.justedlev.account.common.mapper.base;

import java.util.List;

public interface EntityMapper<I, O> {
    O toEntity(I request);

    List<O> toEntities(List<I> requests);
}
