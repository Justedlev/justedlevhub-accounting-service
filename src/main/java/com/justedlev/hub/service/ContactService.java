package com.justedlev.hub.service;

import com.justedlev.hub.api.model.params.ContactFilterParams;
import com.justedlev.hub.api.model.request.CreateContactRequest;
import com.justedlev.hub.api.model.request.PaginationRequest;
import com.justedlev.hub.api.model.request.UpdateContactRequest;
import com.justedlev.hub.api.model.response.ContactResponse;
import com.justedlev.hub.api.model.response.PageResponse;

public interface ContactService {
    PageResponse<ContactResponse> findByFilter(ContactFilterParams params, PaginationRequest request);

    ContactResponse create(CreateContactRequest request);

    ContactResponse update(UpdateContactRequest request);
}
