package com.justedlev.account.component.contact;

import com.justedlev.account.model.request.CreateContactRequest;
import com.justedlev.account.repository.entity.Contact;

public interface ContactComponent {
    Contact create(CreateContactRequest request);
}
