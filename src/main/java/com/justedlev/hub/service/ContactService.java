package com.justedlev.hub.service;

import com.justedlev.hub.model.params.ContactFilterParams;
import com.justedlev.hub.model.request.CreateContactRequest;
import com.justedlev.hub.model.request.UpdateContactRequest;
import com.justedlev.hub.model.response.ContactResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    Page<ContactResponse> findByFilter(ContactFilterParams params, Pageable pageable);

    ContactResponse create(CreateContactRequest request);

    ContactResponse update(UpdateContactRequest request);
}
