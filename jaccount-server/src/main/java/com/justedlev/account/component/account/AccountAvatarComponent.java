package com.justedlev.account.component.account;

import com.justedlev.account.repository.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface AccountAvatarComponent {
    Account updateAvatar(String nickname, MultipartFile photo);
}
