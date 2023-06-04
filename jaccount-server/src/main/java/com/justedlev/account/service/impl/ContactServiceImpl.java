package com.justedlev.account.service.impl;

import com.justedlev.account.component.ContactFinder;
import com.justedlev.account.model.params.ContactFilterParams;
import com.justedlev.account.model.request.CreateContactRequest;
import com.justedlev.account.model.request.UpdateContactRequest;
import com.justedlev.account.model.response.ContactResponse;
import com.justedlev.account.repository.specification.filter.ContactFilter;
import com.justedlev.account.service.ContactService;
import com.justedlev.common.model.request.PaginationRequest;
import com.justedlev.common.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactFinder contactFinder;
    private final ModelMapper defaultMapper;

    @Override
    public PageResponse<ContactResponse> findByFilter(ContactFilterParams params, PaginationRequest request) {
        var filter = defaultMapper.map(params, ContactFilter.class);
        var page = contactFinder.findPageByFilter(filter, request.toPageable());

        return PageResponse.from(page, contact -> defaultMapper.map(contact, ContactResponse.class));
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
