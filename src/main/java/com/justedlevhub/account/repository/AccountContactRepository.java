package com.justedlevhub.account.repository;

import com.justedlevhub.account.repository.entity.AccountContact;
import com.justedlevhub.account.repository.entity.embedabble.AccountContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountContactRepository extends JpaRepository<AccountContact, AccountContactId>,
        JpaSpecificationExecutor<AccountContact> {
    @Query("SELECT AC FROM AccountContact AC WHERE AC.id.accountId IN ?1")
    List<AccountContact> findAllByAccountIds(Collection<UUID> accountIds);

    @Query("SELECT AC FROM AccountContact AC WHERE AC.id.accountId IN ?1 AND AC.main = ?2")
    List<AccountContact> findAllMainByAccountIds(Collection<UUID> accountIds, boolean main);

    @Query("SELECT AC FROM AccountContact AC WHERE AC.id.accountId IN ?1 AND AC.main = ?2")
    Optional<AccountContact> findMainByAccountId(UUID accountId, boolean main);

    @Query("SELECT AC FROM AccountContact AC WHERE AC.id.contactId IN ?1")
    List<AccountContact> findAllByContactIds(Collection<UUID> contactIds);

    @Query("SELECT AC FROM AccountContact AC WHERE AC.id.contactId IN ?1 AND AC.main = ?2")
    List<AccountContact> findAllMainByContactIds(Collection<UUID> contactIds, boolean main);

    @Query("SELECT AC FROM AccountContact AC WHERE AC.id.contactId IN ?1 AND AC.main = ?2")
    Optional<AccountContact> findMainByContactId(UUID contactId, boolean main);
}