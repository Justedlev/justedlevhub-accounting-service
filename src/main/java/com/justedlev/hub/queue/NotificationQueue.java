package com.justedlev.hub.queue;

import com.justedlev.hub.api.notification.model.request.SendNotificationRequest;

public interface NotificationQueue {
    void sendEmail(SendNotificationRequest request);
}
