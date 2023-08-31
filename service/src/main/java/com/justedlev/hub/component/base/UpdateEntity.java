package com.justedlev.hub.component.base;

public interface UpdateEntity<I, O> {
    O update(O entity, I request);
}
