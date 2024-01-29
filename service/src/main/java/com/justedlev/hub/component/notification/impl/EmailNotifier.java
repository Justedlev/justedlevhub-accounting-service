package com.justedlev.hub.component.notification.impl;

import com.justedlev.common.constant.MailSubjectConstant;
import com.justedlev.common.constant.MailTemplateConstant;
import com.justedlev.hub.api.notification.model.request.SendNotificationRequest;
import com.justedlev.hub.component.notification.NotificationCommand;
import com.justedlev.hub.component.notification.NotificationType;
import com.justedlev.hub.component.notification.TypedNotifier;
import com.justedlev.hub.configuration.properties.ApplicationProperties;
import com.justedlev.hub.controller.AccountController;
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
    private final ApplicationProperties.Service properties;

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
                .path(AccountController.ACCOUNTS)
                .path(AccountController.CONFIRM)
                .path("/" + account.getConfirmCode())
                .build().toUriString();

        return Map.of(
                "{FULL_NAME}", account.getNickname(),
                "{CONFIRMATION_LINK}", confirmationLink,
                "{BEST_REGARDS_FROM}", properties.getName()
        );
    }
}
