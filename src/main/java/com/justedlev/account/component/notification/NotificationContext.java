package com.justedlev.account.component.notification;

import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationContext {
    private NotificationType type;
    private Account account;
    private Contact contact;
}