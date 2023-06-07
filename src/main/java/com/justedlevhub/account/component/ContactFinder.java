package com.justedlevhub.account.component;

import com.justedlevhub.account.repository.entity.Contact;
import com.justedlevhub.account.repository.specification.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactFinder {
    Page<Contact> findPageByFilter(ContactFilter filter, Pageable pageable);
}
