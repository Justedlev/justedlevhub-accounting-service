package com.justedlev.hub.account.component.contact.impl;

import com.justedlev.hub.account.component.contact.ContactComponent;
import com.justedlev.hub.account.repository.ContactRepository;
import com.justedlev.hub.account.repository.entity.Contact;
import com.justedlev.hub.api.model.request.CreateContactRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactComponentImpl implements ContactComponent {
    private final ContactRepository contactRepository;
    private final ModelMapper mapper;

    @Override
    public Contact create(CreateContactRequest request) {
//        var filter = ContactFilter.builder()
//                .type(request.getType())
//                .value(request.getValue())
//                .build();
//
//        if (contactRepository.exists(ContactSpecification.from(filter))) {
//            throw new EntityExistsException("Already exist");
//        }

        var contact = mapper.map(request, Contact.class);

        return contactRepository.save(contact);
    }
}
