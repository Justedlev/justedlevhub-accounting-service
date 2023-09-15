package com.justedlev.hub.repository;

import com.justedlev.hub.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends
        JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account>, RevisionRepository<Account, UUID, Long> {
    boolean existsByNickname(String nickname);

    @Transactional
    void deleteByNickname(String nickname);

    Optional<Account> findByConfirmCodeAndStatus(String confirmCode, String status);

    Optional<Account> findByNickname(String nickname);
}
