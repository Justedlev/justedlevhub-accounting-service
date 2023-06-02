package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.ContactJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ContactJournalRepository extends
        JpaRepository<ContactJournal, UUID>, JpaSpecificationExecutor<ContactJournal> {
}