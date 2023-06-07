package com.justedlevhub.account.api.queue;

import com.justedlevhub.api.model.request.SendTemplateMailRequest;

public interface JNotificationQueue {
    void sendEmail(SendTemplateMailRequest request);
}
