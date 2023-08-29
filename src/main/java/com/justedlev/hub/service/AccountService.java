package com.justedlev.hub.service;

import com.justedlev.hub.api.model.params.AccountFilterParams;
import com.justedlev.hub.api.model.request.CreateAccountRequest;
import com.justedlev.hub.api.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.api.model.request.UpdateAccountRequest;
import com.justedlev.hub.api.model.response.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Page<AccountResponse> findPageByFilter(AccountFilterParams params, Pageable pageable);

    AccountResponse findByNickname(String nickname);

    AccountResponse findById(UUID id);

    void confirm(String code);

    AccountResponse updateByNickname(String nickname, UpdateAccountRequest request);

    AccountResponse updateAvatar(String nickname, MultipartFile photo);

    List<AccountResponse> updateMode(UpdateAccountModeRequest request);

    AccountResponse create(CreateAccountRequest request);
}
