package com.justedlev.hub.account.component.notification.manager;

import com.justedlev.hub.account.component.notification.NotificationCommand;

public interface NotificationManager {
    void notice(NotificationCommand context);
}
