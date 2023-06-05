package com.justedlev.account.service;

import com.justedlev.account.model.params.ContactFilterParams;
import com.justedlev.account.model.request.CreateContactRequest;
import com.justedlev.account.model.request.UpdateContactRequest;
import com.justedlev.account.model.response.ContactResponse;
import com.justedlev.common.model.request.PaginationRequest;
import com.justedlev.common.model.response.PageResponse;

public interface ContactService {
    PageResponse<ContactResponse> findByFilter(ContactFilterParams params, PaginationRequest request);

    ContactResponse create(CreateContactRequest request);

    ContactResponse update(UpdateContactRequest request);
}
