package com.justedlev.account.component.impl;

import com.justedlev.account.component.AccountContactComponent;
import com.justedlev.account.component.RegistrationComponent;
import com.justedlev.account.component.account.AccountComponent;
import com.justedlev.account.component.command.CreateAccountContactCommand;
import com.justedlev.account.component.contact.ContactComponent;
import com.justedlev.account.component.notification.NotificationContext;
import com.justedlev.account.component.notification.NotificationType;
import com.justedlev.account.component.notification.manager.NotificationManager;
import com.justedlev.account.enumeration.ContactType;
import com.justedlev.account.model.request.CreateAccountRequest;
import com.justedlev.account.model.request.CreateContactRequest;
import com.justedlev.account.model.request.RegistrationRequest;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegistrationComponentImpl implements RegistrationComponent {
    private final AccountComponent accountComponent;
    private final ContactComponent contactComponent;
    private final AccountContactComponent accountContactComponent;
    private final NotificationManager notificationManager;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public void registration(RegistrationRequest request) {
        var account = createAccount(request);
        var contact = createContact(request);
        createAccountContact(account, contact);
        sendConfirmation(account, contact);
    }

    private void createAccountContact(Account account, Contact contact) {
        var cmd = CreateAccountContactCommand.builder()
                .main(true)
                .accountId(account.getId())
                .contactId(contact.getId())
                .build();
        accountContactComponent.create(cmd);
    }

    private void sendConfirmation(Account account, Contact contact) {
        var context = NotificationContext.builder()
                .type(NotificationType.CONFIRMATION_EMAIL)
                .contact(contact)
                .account(account)
                .build();
        notificationManager.notice(context);
    }

    private Contact createContact(RegistrationRequest request) {
        var createContactRequest = CreateContactRequest.builder()
                .value(request.getEmail())
                .type(ContactType.EMAIL)
                .build();

        return contactComponent.create(createContactRequest);
    }

    private Account createAccount(RegistrationRequest request) {
        var createAccountRequest = mapper.map(request, CreateAccountRequest.class);

        return accountComponent.create(createAccountRequest);
    }
}
