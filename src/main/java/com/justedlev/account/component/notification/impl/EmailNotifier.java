package com.justedlev.account.component.notification.impl;

import com.justedlev.account.client.AccountV1Endpoints;
import com.justedlev.account.component.notification.NotificationContext;
import com.justedlev.account.component.notification.NotificationType;
import com.justedlev.account.component.notification.TypedNotifier;
import com.justedlev.account.constant.MailSubjectConstant;
import com.justedlev.account.constant.MailTemplateConstant;
import com.justedlev.account.properties.JAccountProperties;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.notification.model.request.SendTemplateMailRequest;
import com.justedlev.notification.queue.JNotificationQueue;
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
    public void notice(NotificationContext context) {
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
