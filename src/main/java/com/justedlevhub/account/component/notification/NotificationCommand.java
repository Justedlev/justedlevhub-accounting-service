package com.justedlevhub.account.component.notification;

import com.justedlevhub.account.repository.entity.Account;
import com.justedlevhub.account.repository.entity.Contact;
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