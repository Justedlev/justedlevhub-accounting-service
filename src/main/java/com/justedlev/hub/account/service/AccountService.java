package com.justedlev.hub.account.service;

import com.justedlev.hub.api.model.request.AccountFilterRequest;
import com.justedlev.hub.api.model.request.CreateAccountRequest;
import com.justedlev.hub.api.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.api.model.request.UpdateAccountRequest;
import com.justedlev.hub.api.model.response.AccountResponse;
import com.justedlev.hub.api.model.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    PageResponse<AccountResponse> findPageByFilter(AccountFilterRequest request);

    AccountResponse findByNickname(String nickname);

    String confirm(String code);

    AccountResponse update(UpdateAccountRequest request);

    AccountResponse updateAvatar(String nickname, MultipartFile photo);

    List<AccountResponse> updateMode(UpdateAccountModeRequest request);

    AccountResponse create(CreateAccountRequest request);
}
