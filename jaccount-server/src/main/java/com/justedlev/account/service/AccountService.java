package com.justedlev.account.service;

import com.justedlev.account.model.request.AccountFilterRequest;
import com.justedlev.account.model.request.CreateAccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.request.UpdateAccountRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.common.model.response.PageResponse;
import com.justedlev.common.model.response.ReportResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    PageResponse<AccountResponse> findPageByFilter(AccountFilterRequest request);

    AccountResponse findByNickname(String nickname);

    ReportResponse confirm(String code);

    AccountResponse update(UpdateAccountRequest request);

    AccountResponse updateAvatar(String nickname, MultipartFile photo);

    List<AccountResponse> updateMode(UpdateAccountModeRequest request);

    AccountResponse create(CreateAccountRequest request);
}
