package com.justedlev.hub.repository;

import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.type.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>,
        JpaSpecificationExecutor<Account>, RevisionRepository<Account, UUID, Long> {

    boolean existsByNickname(String nickname);

    boolean existsByConfirmCodeAndStatus(String confirmCode, String status);

    default boolean isUnconfirmed(String code) {
        return existsByConfirmCodeAndStatus(code, AccountStatus.UNCONFIRMED.getLabel());
    }

    Optional<Account> findByConfirmCode(String code);
}
