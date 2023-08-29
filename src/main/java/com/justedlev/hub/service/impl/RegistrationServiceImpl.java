package com.justedlev.hub.service.impl;

import com.justedlev.hub.api.model.request.RegistrationRequest;
import com.justedlev.hub.component.EmailNotificationComponent;
import com.justedlev.hub.component.account.AccountValidator;
import com.justedlev.hub.repository.ContactRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final EmailNotificationComponent emailNotificationComponent;
    private final ContactRepository contactRepository;
    private final AccountValidator accountValidator;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public void registration(RegistrationRequest request) {
        accountValidator.validateNickname(request.getNickname());
        var account = mapper.map(request, Account.class);
        var contact = mapper.map(request, Contact.class);
        contact.setAccount(account);
        contactRepository.save(contact);
        emailNotificationComponent.sendConfirmation(account, contact);
    }
}
