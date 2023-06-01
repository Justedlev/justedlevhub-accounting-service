package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.AccountJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountJournalRepository extends
        JpaRepository<AccountJournal, Long>, JpaSpecificationExecutor<AccountJournal> {
}