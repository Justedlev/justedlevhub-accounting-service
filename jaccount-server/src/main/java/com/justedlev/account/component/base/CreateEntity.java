package com.justedlev.account.component.base;

public interface CreateEntity<I, O> {
    O create(I request);
}
