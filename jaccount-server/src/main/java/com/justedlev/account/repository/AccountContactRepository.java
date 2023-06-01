package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.AccountContact;
import com.justedlev.account.repository.entity.embedabble.AccountContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountContactRepository extends
        JpaRepository<AccountContact, AccountContactId>, JpaSpecificationExecutor<AccountContact> {
}