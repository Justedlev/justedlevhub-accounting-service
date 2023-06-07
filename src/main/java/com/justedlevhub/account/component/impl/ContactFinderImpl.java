package com.justedlevhub.account.component.impl;

import com.justedlevhub.account.component.ContactFinder;
import com.justedlevhub.account.repository.ContactRepository;
import com.justedlevhub.account.repository.entity.Contact;
import com.justedlevhub.account.repository.specification.ContactSpecification;
import com.justedlevhub.account.repository.specification.filter.ContactFilter;
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
