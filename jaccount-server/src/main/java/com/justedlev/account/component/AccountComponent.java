package com.justedlev.account.component;

import com.justedlev.account.component.base.CreateEntity;
import com.justedlev.account.component.base.DeleteEntity;
import com.justedlev.account.component.base.SaveEntity;
import com.justedlev.account.component.base.UpdateEntity;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.repository.custom.filter.AccountFilter;
import com.justedlev.account.repository.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AccountComponent extends UpdateEntity<AccountRequest, Account>, CreateEntity<AccountRequest, Account>,
        SaveEntity<Account>, DeleteEntity<Account> {
    List<Account> findByFilter(AccountFilter filter);

    Page<Account> findPageByFilter(AccountFilter filter, Pageable pageable);

    Page<Account> findPage(Pageable pageable);

    Account confirm(String activationCode);

    Account update(String nickname, AccountRequest request);

    Account deactivate(String nickname);

    Account activate(String nickname);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByNickname(String nickname);

    Account update(String nickname, MultipartFile photo);
}
