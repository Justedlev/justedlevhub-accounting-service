package com.justedlev.hub.component.notification.impl;

import com.justedlev.hub.api.notification.model.request.SendNotificationRequest;
import com.justedlev.hub.component.notification.NotificationCommand;
import com.justedlev.hub.component.notification.NotificationType;
import com.justedlev.hub.component.notification.TypedNotifier;
import com.justedlev.hub.configuration.properties.Properties;
import com.justedlev.hub.constant.ControllerResources;
import com.justedlev.hub.constant.MailSubjectConstant;
import com.justedlev.hub.constant.MailTemplateConstant;
import com.justedlev.hub.queue.NotificationQueue;
import com.justedlev.hub.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailNotifier implements TypedNotifier {
    private final NotificationQueue notificationQueue;
    private final Properties.Service properties;

    @Override
    public void send(NotificationCommand context) {
        var content = buildContent(context.getAccount());
        var recipient = context.getContact().getValue();
        var subject = String.format(MailSubjectConstant.CONFIRMATION, properties.getName());
        var mail = SendNotificationRequest.builder()
                .templateName(MailTemplateConstant.ACCOUNT_CONFIRMATION)
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .type("email")
                .build();
        notificationQueue.sendEmail(mail);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.CONFIRMATION_EMAIL;
    }

    private Map<String, String> buildContent(Account account) {
        var confirmationLink = UriComponentsBuilder.fromHttpUrl(properties.getUrl())
                .path(ControllerResources.ACCOUNTS)
                .path(ControllerResources.CONFIRM)
                .path("/" + account.getActivationCode())
                .build().toUriString();

        return Map.of(
                "{FULL_NAME}", account.getNickname(),
                "{CONFIRMATION_LINK}", confirmationLink,
                "{BEST_REGARDS_FROM}", properties.getName()
        );
    }
}
