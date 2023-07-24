package com.justedlev.hub.component.base;

public interface CreateEntity<I, O> {
    O create(I request);
}
