package com.justedlev.hub.configuration;

import com.justedlev.hub.component.HibernateEntityListener;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HibernateEntityEventListenerConfiguration {
    private final EntityManagerFactory entityManagerFactory;
    private final HibernateEntityListener hibernateEntityListener;

    @PostConstruct
    public void registerListeners() {
        var service = entityManagerFactory.unwrap(SessionFactoryImpl.class)
                .getServiceRegistry()
                .getService(EventListenerRegistry.class);
        service.appendListeners(EventType.POST_COMMIT_INSERT, hibernateEntityListener);
        service.appendListeners(EventType.POST_COMMIT_UPDATE, hibernateEntityListener);
        service.appendListeners(EventType.POST_COMMIT_DELETE, hibernateEntityListener);
    }
}
