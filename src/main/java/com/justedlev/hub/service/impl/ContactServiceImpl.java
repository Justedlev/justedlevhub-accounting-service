package com.justedlev.hub.service.impl;

import com.justedlev.hub.component.ContactFinder;
import com.justedlev.hub.repository.specification.filter.ContactFilter;
import com.justedlev.hub.service.ContactService;
import com.justedlev.hub.api.model.params.ContactFilterParams;
import com.justedlev.hub.api.model.request.CreateContactRequest;
import com.justedlev.hub.api.model.request.PaginationRequest;
import com.justedlev.hub.api.model.request.UpdateContactRequest;
import com.justedlev.hub.api.model.response.ContactResponse;
import com.justedlev.hub.api.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactFinder contactFinder;
    private final ModelMapper mapper;

    @Override
    public PageResponse<ContactResponse> findByFilter(ContactFilterParams params, PaginationRequest request) {
        var filter = mapper.map(params, ContactFilter.class);
        var page = contactFinder.findPageByFilter(filter, request.toPageable());

        return PageResponse.from(page, contact -> mapper.map(contact, ContactResponse.class));
    }

    @Override
    public ContactResponse create(CreateContactRequest request) {
        return null;
    }

    @Override
    public ContactResponse update(UpdateContactRequest request) {
        return null;
    }
}
