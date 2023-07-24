package com.justedlev.hub.repository;

import com.justedlev.hub.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>,
        JpaSpecificationExecutor<Account>,
        RevisionRepository<Account, UUID, Long> {
    List<Account> findByNickname(String nickname);
}
