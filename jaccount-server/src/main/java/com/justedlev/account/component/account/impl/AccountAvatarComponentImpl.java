package com.justedlev.account.component.account.impl;

import com.justedlev.account.component.account.AccountAvatarComponent;
import com.justedlev.account.component.account.AccountFinder;
import com.justedlev.account.enumeration.AccountStatusCode;
import com.justedlev.account.repository.AccountRepository;
import com.justedlev.account.repository.entity.Account;
import com.justedlev.account.repository.entity.Avatar;
import com.justedlev.account.repository.specification.filter.AccountFilter;
import com.justedlev.storage.client.JStorageFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountAvatarComponentImpl implements AccountAvatarComponent {
    private final AccountFinder accountFinder;
    private final AccountRepository accountRepository;
    private final JStorageFeignClient storageFeignClient;
    private final ModelMapper mapper;

    @Override
    public Account updateAvatar(String nickname, MultipartFile file) {
        var filter = AccountFilter.builder()
                .nickname(nickname)
                .excludeStatus(AccountStatusCode.DELETED)
                .build();
        var account = accountFinder.findByFilter(filter)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Not exist"));
        setAvatar(account, file);

        return accountRepository.save(account);
    }

    private void setAvatar(Account account, MultipartFile file) {
        Optional.ofNullable(account.getAvatar())
                .map(Avatar::getFileId)
                .ifPresent(storageFeignClient::delete);
        storageFeignClient.upload(List.of(file))
                .stream()
                .findFirst()
                .map(current -> mapper.map(current, Avatar.class))
                .ifPresent(account::setAvatar);
    }
}
