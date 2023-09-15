package com.justedlev.hub.service;

import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    Page<AccountResponse> findPageByFilter(AccountFilterParams params, Pageable pageable);

    AccountResponse getByNickname(String nickname);

    AccountResponse confirm(String code);

    AccountResponse updateByNickname(String nickname, UpdateAccountRequest request);

    AccountResponse updateAvatar(String nickname, MultipartFile photo);

    List<AccountResponse> updateMode(UpdateAccountModeRequest request);

    AccountResponse create(CreateAccountRequest request);

    void deleteByNickname(String nickname);
}
