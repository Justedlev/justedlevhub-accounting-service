package com.justedlev.hub.api.queue.impl;

import com.justedlev.hub.properties.CloudAmqpProperties;
import com.justedlev.hub.api.model.request.SendTemplateMailRequest;
import com.justedlev.hub.api.queue.JNotificationQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JNotificationQueueImpl implements JNotificationQueue {
    private final AmqpTemplate amqpTemplate;
    private final CloudAmqpProperties.Queue properties;

    @Override
    public void sendEmail(SendTemplateMailRequest request) {
        amqpTemplate.convertAndSend(properties.getSendTemplateMail(), request);
    }
}
