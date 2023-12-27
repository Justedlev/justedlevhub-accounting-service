package com.justedlev.hub.component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class HibernateEntityListener implements PostCommitInsertEventListener, PostCommitUpdateEventListener, PostCommitDeleteEventListener {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public void onPostDeleteCommitFailed(PostDeleteEvent event) {
        log.debug("{}", event);
    }

    @Override
    public void onPostInsertCommitFailed(PostInsertEvent event) {
        log.debug("{}", event);
    }

    @Override
    public void onPostUpdateCommitFailed(PostUpdateEvent event) {
        log.debug("{}", event);
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        log.debug("{}", event);
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        log.debug("{}", event);
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        String[] properties = event.getPersister().getPropertyNames();
        Object[] oldState = event.getOldState();
        Object[] newState = event.getState();
        var oldStateMap = new HashMap<>();
        var newStateMap = new HashMap<>();

        for (int i = 0; i < properties.length; i++) {
            oldStateMap.put(properties[i], oldState[i]);
            newStateMap.put(properties[i], newState[i]);
        }

        log.debug("entity: {}; old state: {}, new state: {}", event.getEntity(), oldStateMap, newStateMap);
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return true;
    }
}
