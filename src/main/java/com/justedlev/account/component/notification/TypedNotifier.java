package com.justedlev.account.component.notification;

public interface TypedNotifier {
    void notice(NotificationContext context);

    NotificationType getType();
}
