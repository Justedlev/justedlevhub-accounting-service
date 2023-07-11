package com.justedlev.hub.account.component.account;

import com.justedlev.hub.account.repository.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface AccountAvatarComponent {
    Account updateAvatar(String nickname, MultipartFile photo);
}
