package com.justedlev.hub.component.contact;

import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.api.model.request.CreateContactRequest;

public interface ContactComponent {
    Contact create(CreateContactRequest request);
}
