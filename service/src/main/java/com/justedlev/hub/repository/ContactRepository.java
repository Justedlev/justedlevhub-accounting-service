package com.justedlev.hub.repository;

import com.justedlev.hub.repository.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID>,
        JpaSpecificationExecutor<Contact>,
        RevisionRepository<Contact, UUID, Long> {
}
