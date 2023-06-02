package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.AccountJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AccountJournalRepository extends
        JpaRepository<AccountJournal, UUID>, JpaSpecificationExecutor<AccountJournal> {
}