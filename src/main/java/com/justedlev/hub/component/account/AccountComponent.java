package com.justedlev.hub.component.account;

import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.specification.filter.AccountFilter;
import com.justedlev.hub.api.model.request.CreateAccountRequest;
import com.justedlev.hub.api.model.request.UpdateAccountRequest;
import com.justedlev.hub.component.base.CreateEntity;
import com.justedlev.hub.component.base.DeleteEntity;
import com.justedlev.hub.component.base.SaveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountComponent extends CreateEntity<CreateAccountRequest, Account>,
        SaveEntity<Account>, DeleteEntity<Account> {
    List<Account> findByFilter(AccountFilter filter);

    Page<Account> findPageByFilter(AccountFilter filter, Pageable pageable);

    Account confirm(String activationCode);

    Account deactivate(String nickname);

    Account activate(String nickname);

    Optional<Account> findByNickname(String nickname);

    Account updateByNickname(String nickname, UpdateAccountRequest request);
}
