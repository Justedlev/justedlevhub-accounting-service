package com.justedlev.account.component.impl;

import com.justedlev.account.component.ContactFinder;
import com.justedlev.account.repository.ContactRepository;
import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.specification.ContactSpecification;
import com.justedlev.account.repository.specification.filter.ContactFilter;
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
