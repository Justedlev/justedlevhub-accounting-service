package com.justedlev.hub.component.impl;

import com.justedlev.hub.component.notification.NotificationCommand;
import com.justedlev.hub.component.notification.NotificationType;
import com.justedlev.hub.component.notification.manager.NotificationManager;
import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.ContactRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.repository.specification.AccountSpecification;
import com.justedlev.hub.repository.specification.filter.AccountFilter;
import com.justedlev.hub.api.model.request.RegistrationRequest;
import com.justedlev.hub.api.type.AccountStatus;
import com.justedlev.hub.component.RegistrationComponent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Component
@RequiredArgsConstructor
public class RegistrationComponentImpl implements RegistrationComponent {
    private final AccountRepository accountRepository;
    private final ContactRepository contactRepository;
    private final NotificationManager notificationManager;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public void registration(RegistrationRequest request) {
        validateNickname(request.getNickname());
        var account = mapper.map(request, Account.class);
        var contact = mapper.map(request, Contact.class);
        contact.setAccount(account);
        contactRepository.save(contact);
        sendConfirmation(account, contact);
    }

    private void sendConfirmation(Account account, Contact contact) {
        var context = NotificationCommand.builder()
                .type(NotificationType.CONFIRMATION_EMAIL)
                .contact(contact)
                .account(account)
                .build();
        notificationManager.notice(context);
    }

    private void validateNickname(String nickname) {
        var filter = AccountFilter.builder()
                .nickname(nickname)
                .excludeStatus(AccountStatus.DELETED)
                .build();

        if (accountRepository.exists(AccountSpecification.from(filter))) {
            throw new EntityExistsException(
                    String.format("Account %s already exists", nickname));
        }
    }
}
