package com.justedlev.hub.component.notification.manager;

import com.justedlev.hub.component.notification.NotificationCommand;

public interface NotificationManager {
    void send(NotificationCommand context);
}
