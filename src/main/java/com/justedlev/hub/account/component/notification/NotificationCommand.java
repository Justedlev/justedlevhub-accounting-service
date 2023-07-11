package com.justedlev.hub.account.component.notification;

import com.justedlev.hub.account.repository.entity.Account;
import com.justedlev.hub.account.repository.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCommand {
    private NotificationType type;
    private Account account;
    private Contact contact;
}