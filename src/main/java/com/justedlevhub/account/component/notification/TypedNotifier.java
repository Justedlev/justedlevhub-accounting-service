package com.justedlevhub.account.component.notification;

public interface TypedNotifier {
    void notice(NotificationCommand context);

    NotificationType getType();
}
