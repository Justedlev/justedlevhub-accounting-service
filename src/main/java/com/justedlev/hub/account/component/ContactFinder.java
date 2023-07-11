package com.justedlev.hub.account.component;

import com.justedlev.hub.account.repository.entity.Contact;
import com.justedlev.hub.account.repository.specification.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactFinder {
    Page<Contact> findPageByFilter(ContactFilter filter, Pageable pageable);
}
