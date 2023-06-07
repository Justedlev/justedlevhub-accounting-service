package com.justedlevhub.account.component.base;

import java.util.List;

public interface SaveEntity<E> {
    E save(E entity);

    List<E> saveAll(List<E> entities);
}
