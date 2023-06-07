package com.justedlevhub.account.component.contact;

import com.justedlevhub.account.repository.entity.Contact;
import com.justedlevhub.api.model.request.CreateContactRequest;

public interface ContactComponent {
    Contact create(CreateContactRequest request);
}
