package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
    List<Account> findByNicknameIn(Collection<String> nicknames);

//    List<Account> findByContactsEmailIn(Collection<String> emails);
}
