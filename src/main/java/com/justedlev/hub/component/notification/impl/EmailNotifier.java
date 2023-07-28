package com.justedlev.hub.component.notification.impl;

import com.justedlev.hub.api.model.request.SendTemplateMailRequest;
import com.justedlev.hub.api.queue.JNotificationQueue;
import com.justedlev.hub.component.notification.NotificationCommand;
import com.justedlev.hub.component.notification.NotificationType;
import com.justedlev.hub.component.notification.TypedNotifier;
import com.justedlev.hub.constant.AccountV1Endpoints;
import com.justedlev.hub.constant.MailSubjectConstant;
import com.justedlev.hub.constant.MailTemplateConstant;
import com.justedlev.hub.properties.JAccountProperties;
import com.justedlev.hub.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailNotifier implements TypedNotifier {
    private final JNotificationQueue notificationQueue;
    private final JAccountProperties properties;

    @Override
    public void notice(NotificationCommand context) {
        var content = buildContent(context.getAccount());
        var recipient = context.getContact().getValue();
        var subject = String.format(MailSubjectConstant.CONFIRMATION, properties.getService().getName());
        var mail = SendTemplateMailRequest.builder()
                .templateName(MailTemplateConstant.ACCOUNT_CONFIRMATION)
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .build();
        notificationQueue.sendEmail(mail);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.CONFIRMATION_EMAIL;
    }

    private Map<String, String> buildContent(Account account) {
        var confirmationLink = UriComponentsBuilder.fromHttpUrl(properties.getService().getHost())
                .path(AccountV1Endpoints.V1_ACCOUNT_CONFIRM)
                .path("/" + account.getActivationCode())
                .build().toUriString();

        return Map.of(
                "{FULL_NAME}", account.getNickname(),
                "{CONFIRMATION_LINK}", confirmationLink,
                "{BEST_REGARDS_FROM}", properties.getService().getName()
        );
    }
}
