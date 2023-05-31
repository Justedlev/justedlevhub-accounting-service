package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.ContactJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactJournalRepository extends JpaRepository<ContactJournal, Long>, JpaSpecificationExecutor<ContactJournal> {
}