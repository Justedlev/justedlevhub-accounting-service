package com.justedlevhub.account.service.impl;

import com.justedlevhub.account.component.ContactFinder;
import com.justedlevhub.account.repository.specification.filter.ContactFilter;
import com.justedlevhub.account.service.ContactService;
import com.justedlevhub.api.model.params.ContactFilterParams;
import com.justedlevhub.api.model.request.CreateContactRequest;
import com.justedlevhub.api.model.request.PaginationRequest;
import com.justedlevhub.api.model.request.UpdateContactRequest;
import com.justedlevhub.api.model.response.ContactResponse;
import com.justedlevhub.api.model.response.PageResponse;
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
