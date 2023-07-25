package com.justedlev.hub.component.impl;

import com.justedlev.hub.repository.ContactRepository;
import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.repository.specification.ContactSpecification;
import com.justedlev.hub.repository.specification.filter.ContactFilter;
import com.justedlev.hub.component.ContactFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactFinderImpl implements ContactFinder {
    private final ContactRepository contactRepository;

    @Override
    public Page<Contact> findPageByFilter(ContactFilter filter, Pageable pageable) {
        return contactRepository.findAll(ContactSpecification.from(filter), pageable);
    }
}
