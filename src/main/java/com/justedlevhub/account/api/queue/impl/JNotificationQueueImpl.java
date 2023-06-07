package com.justedlevhub.account.api.queue.impl;

import com.justedlevhub.account.api.queue.JNotificationQueue;
import com.justedlevhub.account.properties.CloudAmqpProperties;
import com.justedlevhub.api.model.request.SendTemplateMailRequest;
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
