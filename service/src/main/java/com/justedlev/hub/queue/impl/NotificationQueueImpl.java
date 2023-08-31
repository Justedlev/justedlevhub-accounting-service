package com.justedlev.hub.queue.impl;

import com.justedlev.hub.api.notification.model.request.SendNotificationRequest;
import com.justedlev.hub.configuration.properties.CloudAmqpProperties;
import com.justedlev.hub.queue.NotificationQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationQueueImpl implements NotificationQueue {
    private final AmqpTemplate amqpTemplate;
    private final CloudAmqpProperties.Queue properties;

    @Override
    public void sendEmail(SendNotificationRequest request) {
        amqpTemplate.convertAndSend(properties.getSendTemplateMail(), request);
    }
}
