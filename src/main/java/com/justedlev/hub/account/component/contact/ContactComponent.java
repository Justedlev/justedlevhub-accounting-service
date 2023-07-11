package com.justedlev.hub.account.component.contact;

import com.justedlev.hub.account.repository.entity.Contact;
import com.justedlev.hub.api.model.request.CreateContactRequest;

public interface ContactComponent {
    Contact create(CreateContactRequest request);
}
