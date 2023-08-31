package com.justedlev.hub.component;

import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;

public interface EmailNotificationComponent {
    void sendConfirmation(Account account, Contact contact);
}