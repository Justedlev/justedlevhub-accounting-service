package com.justedlev.hub.component;

import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;

public interface EmailNotificationSender {
    void sendConfirmation(Account account, Contact contact);
}