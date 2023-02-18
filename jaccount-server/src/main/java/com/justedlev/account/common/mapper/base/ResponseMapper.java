package com.justedlev.account.common.mapper.base;

import java.util.List;

public interface ResponseMapper<I, O> {
    O toResponse(I request);

    List<O> toResponses(List<I> requests);
}
