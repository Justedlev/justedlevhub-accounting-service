package com.justedlev.hub.component.contact.impl;

import com.justedlev.hub.component.contact.ContactComponent;
import com.justedlev.hub.model.request.CreateContactRequest;
import com.justedlev.hub.repository.ContactRepository;
import com.justedlev.hub.repository.entity.Contact;
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
