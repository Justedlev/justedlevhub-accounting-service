package com.justedlev.hub.component.impl;

import com.justedlev.hub.component.EmailNotificationComponent;
import com.justedlev.hub.component.notification.NotificationCommand;
import com.justedlev.hub.component.notification.NotificationType;
import com.justedlev.hub.component.notification.manager.NotificationManager;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationComponentImpl implements EmailNotificationComponent {
    private final NotificationManager notificationManager;

    @Override
    public void sendConfirmation(Account account, Contact contact) {
        var context = NotificationCommand.builder()
                .type(NotificationType.CONFIRMATION_EMAIL)
                .contact(contact)
                .account(account)
                .build();
        notificationManager.send(context);
    }
}
