package com.justedlev.account.repository;

import com.justedlev.account.repository.custom.AccountCustomRepository;
import com.justedlev.account.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account>,
        AccountCustomRepository {
    boolean existsByNickname(String nickname);
}
