package com.justedlev.hub.component.account;

import com.justedlev.hub.repository.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface AccountAvatarComponent {
    Account updateAvatar(String nickname, MultipartFile photo);
}
