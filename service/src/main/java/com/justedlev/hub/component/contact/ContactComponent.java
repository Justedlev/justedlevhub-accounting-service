package com.justedlev.hub.component.contact;

import com.justedlev.hub.model.request.CreateContactRequest;
import com.justedlev.hub.repository.entity.Contact;

public interface ContactComponent {
    Contact create(CreateContactRequest request);
}