package com.justedlev.hub.service.impl;

import com.justedlev.hub.common.mapper.CustomModelMapper;
import com.justedlev.hub.component.ContactFinder;
import com.justedlev.hub.model.params.ContactFilterParams;
import com.justedlev.hub.model.request.CreateContactRequest;
import com.justedlev.hub.model.request.UpdateContactRequest;
import com.justedlev.hub.model.response.ContactResponse;
import com.justedlev.hub.repository.filter.ContactFilter;
import com.justedlev.hub.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactFinder contactFinder;
    private final CustomModelMapper mapper;

    @Override
    public Page<ContactResponse> findByFilter(ContactFilterParams params, Pageable pageable) {
        var filter = mapper.map(params, ContactFilter.class);
        var page = contactFinder.findPageByFilter(filter, pageable);

        return page.map(mapper::map);
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
