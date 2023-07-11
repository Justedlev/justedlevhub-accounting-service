package com.justedlev.hub.account.api.queue;

import com.justedlev.hub.api.model.request.SendTemplateMailRequest;

public interface JNotificationQueue {
    void sendEmail(SendTemplateMailRequest request);
}
