package com.justedlev.hub.account.component.base;

public interface UpdateEntity<I, O> {
    O update(O entity, I request);
}
