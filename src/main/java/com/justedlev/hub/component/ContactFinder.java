package com.justedlev.hub.component;

import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.repository.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactFinder {
    Page<Contact> findPageByFilter(ContactFilter filter, Pageable pageable);
}
