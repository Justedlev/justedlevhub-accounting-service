package com.justedlev.account.repository.custom;

import java.util.List;

public interface FilterableRepository<E, F> {
    List<E> findByFilter(F filter);
}
