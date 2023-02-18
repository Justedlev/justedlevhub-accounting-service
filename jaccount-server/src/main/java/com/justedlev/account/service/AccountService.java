package com.justedlev.account.service;

import com.justedlev.account.model.params.AccountFilterParams;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.common.model.request.PaginationRequest;
import com.justedlev.common.model.response.PageResponse;
import com.justedlev.common.model.response.ReportResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    PageResponse<AccountResponse> getPage(PaginationRequest request);

    PageResponse<AccountResponse> getPageByFilter(AccountFilterParams params, PaginationRequest pagination);

    AccountResponse getByEmail(String email);

    AccountResponse getByNickname(String nickname);

    ReportResponse confirm(String code);

    AccountResponse update(String nickname, AccountRequest request);

    AccountResponse updateAvatar(String nickname, MultipartFile photo);

    List<AccountResponse> updateMode(UpdateAccountModeRequest request);

    AccountResponse create(AccountRequest request);
}
