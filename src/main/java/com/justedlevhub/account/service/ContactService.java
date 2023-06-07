package com.justedlevhub.account.service;

import com.justedlevhub.api.model.params.ContactFilterParams;
import com.justedlevhub.api.model.request.CreateContactRequest;
import com.justedlevhub.api.model.request.PaginationRequest;
import com.justedlevhub.api.model.request.UpdateContactRequest;
import com.justedlevhub.api.model.response.ContactResponse;
import com.justedlevhub.api.model.response.PageResponse;

public interface ContactService {
    PageResponse<ContactResponse> findByFilter(ContactFilterParams params, PaginationRequest request);

    ContactResponse create(CreateContactRequest request);

    ContactResponse update(UpdateContactRequest request);
}
