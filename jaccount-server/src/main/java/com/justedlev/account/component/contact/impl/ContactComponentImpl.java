package com.justedlev.account.component.contact.impl;

import com.justedlev.account.component.contact.ContactComponent;
import com.justedlev.account.model.request.CreateContactRequest;
import com.justedlev.account.repository.ContactRepository;
import com.justedlev.account.repository.entity.Contact;
import com.justedlev.account.repository.specification.ContactSpecification;
import com.justedlev.account.repository.specification.filter.ContactFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;

@Component
@RequiredArgsConstructor
public class ContactComponentImpl implements ContactComponent {
    private final ContactRepository contactRepository;
    private final ModelMapper mapper;

    @Override
    public Contact create(CreateContactRequest request) {
        var filter = ContactFilter.builder()
                .type(request.getType())
                .value(request.getValue())
                .build();

        if (contactRepository.exists(ContactSpecification.from(filter))) {
            throw new EntityExistsException("Already exist");
        }

        var contact = mapper.map(request, Contact.class);

        return contactRepository.save(contact);
    }
}
