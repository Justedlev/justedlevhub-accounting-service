package com.justedlev.hub.account.component.base;

public interface CreateEntity<I, O> {
    O create(I request);
}
