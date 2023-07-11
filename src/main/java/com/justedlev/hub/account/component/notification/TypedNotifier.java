package com.justedlev.hub.account.component.notification;

public interface TypedNotifier {
    void notice(NotificationCommand context);

    NotificationType getType();
}
