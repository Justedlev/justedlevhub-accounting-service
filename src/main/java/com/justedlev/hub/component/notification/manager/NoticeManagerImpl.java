package com.justedlev.hub.component.notification.manager;

import com.justedlev.hub.component.notification.NotificationCommand;
import com.justedlev.hub.component.notification.NotificationType;
import com.justedlev.hub.component.notification.TypedNotifier;
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
    public void notice(NotificationCommand context) {
        typedNotifiers.get(context.getType()).notice(context);
    }
}
