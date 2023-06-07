package com.justedlevhub.account.component.account;

import com.justedlevhub.account.repository.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface AccountAvatarComponent {
    Account updateAvatar(String nickname, MultipartFile photo);
}
