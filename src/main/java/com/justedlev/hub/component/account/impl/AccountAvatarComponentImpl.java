package com.justedlev.hub.component.account.impl;

import com.justedlev.hub.repository.AccountRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Avatar;
import com.justedlev.hub.repository.specification.filter.AccountFilter;
import com.justedlev.hub.api.JStorageFeignClient;
import com.justedlev.hub.api.type.AccountStatus;
import com.justedlev.hub.component.account.AccountAvatarComponent;
import com.justedlev.hub.component.account.AccountFinder;
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
                .excludeStatus(AccountStatus.DELETED)
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
