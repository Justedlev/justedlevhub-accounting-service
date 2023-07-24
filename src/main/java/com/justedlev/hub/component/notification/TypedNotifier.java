package com.justedlev.hub.component.notification;

public interface TypedNotifier {
    void notice(NotificationCommand context);

    NotificationType getType();
}
