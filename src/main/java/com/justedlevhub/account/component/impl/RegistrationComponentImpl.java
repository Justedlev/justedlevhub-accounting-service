package com.justedlevhub.account.component.impl;

import com.justedlevhub.account.component.RegistrationComponent;
import com.justedlevhub.account.component.notification.NotificationCommand;
import com.justedlevhub.account.component.notification.NotificationType;
import com.justedlevhub.account.component.notification.manager.NotificationManager;
import com.justedlevhub.account.repository.AccountRepository;
import com.justedlevhub.account.repository.ContactRepository;
import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.entity.Contact;
import com.justedlevhub.account.repository.specification.AccountSpecification;
import com.justedlevhub.account.repository.specification.filter.AccountFilter;
import com.justedlevhub.api.model.request.RegistrationRequest;
import com.justedlevhub.api.type.AccountStatus;
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
        accountRepository.save(account);
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
