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
import java.util.UUID;

public interface AccountService {
    Page<AccountResponse> findPageByFilter(AccountFilterParams params, Pageable pageable);

    AccountResponse getById(UUID id);

    UUID confirm(String code);

    AccountResponse updateById(UUID id, UpdateAccountRequest request);

    AccountResponse updateAvatar(UUID id, MultipartFile photo);

    List<AccountResponse> updateMode(UpdateAccountModeRequest request);

    AccountResponse create(CreateAccountRequest request);

    void deleteById(UUID id);
}
