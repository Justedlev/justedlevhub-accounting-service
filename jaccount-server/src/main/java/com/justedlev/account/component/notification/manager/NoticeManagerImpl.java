package com.justedlev.account.component.notification.manager;

import com.justedlev.account.component.notification.NotificationContext;
import com.justedlev.account.component.notification.NotificationType;
import com.justedlev.account.component.notification.TypedNotifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NoticeManagerImpl implements NotificationManager {
    private final Map<NotificationType, TypedNotifier> typedNotifiers;

    public NoticeManagerImpl(Set<TypedNotifier> typedNotifiers) {
        this.typedNotifiers = typedNotifiers.stream()
                .collect(Collectors.toMap(TypedNotifier::getType, Function.identity()));
    }

    @Override
    public void notice(NotificationContext context) {
        typedNotifiers.get(context.getType()).notice(context);
    }
}
