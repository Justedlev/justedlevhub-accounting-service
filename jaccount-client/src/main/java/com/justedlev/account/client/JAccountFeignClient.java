package com.justedlev.account.client;

import com.justedlev.account.client.configuration.JAccountFeignClientConfiguration;
import com.justedlev.account.model.params.AccountFilterParams;
import com.justedlev.account.model.request.CreateAccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.common.model.request.PaginationRequest;
import com.justedlev.common.model.response.PageResponse;
import com.justedlev.common.model.response.ReportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name = "jaccount-api-client",
        url = "${jaccount.client.url}",
        configuration = JAccountFeignClientConfiguration.class
)
public interface JAccountFeignClient {
    @PostMapping(value = AccountV1Endpoints.V1_ACCOUNT_PAGE)
    PageResponse<AccountResponse> findPage(AccountFilterParams params, @RequestBody PaginationRequest pagination);

    @PostMapping(value = AccountV1Endpoints.V1_ACCOUNT_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    AccountResponse create(@RequestBody CreateAccountRequest request);

    @GetMapping(value = AccountV1Endpoints.V1_ACCOUNT_NICKNAME)
    AccountResponse getAccountByNickname(@PathVariable String nickname);

    @PutMapping(value = AccountV1Endpoints.V1_ACCOUNT_NICKNAME_UPDATE)
    AccountResponse updateAccount(@PathVariable String nickname, @RequestBody CreateAccountRequest request);

    @PostMapping(
            value = AccountV1Endpoints.V1_ACCOUNT_NICKNAME_UPDATE_AVATAR,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    AccountResponse updateAccountAvatar(@PathVariable String nickname, @RequestPart MultipartFile file);

    @GetMapping(value = AccountV1Endpoints.V1_ACCOUNT_CONFIRM_CODE)
    ReportResponse confirm(@PathVariable String code);

    @PostMapping(value = AccountV1Endpoints.V1_ACCOUNT_UPDATE_MODE)
    List<AccountResponse> updateMode(@RequestBody UpdateAccountModeRequest request);
}
