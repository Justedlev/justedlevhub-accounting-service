package com.justedlev.hub.component.notification;

public interface TypedNotifier {
    void send(NotificationCommand context);

    NotificationType getType();
}
