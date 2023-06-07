package com.justedlevhub.account.controller;

import com.justedlevhub.account.constant.AccountV1Endpoints;
import com.justedlevhub.account.service.AccountService;
import com.justedlevhub.api.model.request.AccountFilterRequest;
import com.justedlevhub.api.model.request.CreateAccountRequest;
import com.justedlevhub.api.model.request.UpdateAccountModeRequest;
import com.justedlevhub.api.model.request.UpdateAccountRequest;
import com.justedlevhub.api.model.response.AccountResponse;
import com.justedlevhub.api.model.response.PageResponse;
import com.justedlevhub.api.model.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(AccountV1Endpoints.V1_ACCOUNT)
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;

    @PostMapping(value = AccountV1Endpoints.PAGE)
    public ResponseEntity<PageResponse<AccountResponse>> findPage(@Valid @RequestBody AccountFilterRequest request) {
        return ResponseEntity.ok(accountService.findPageByFilter(request));
    }

    @GetMapping(value = AccountV1Endpoints.NICKNAME)
    public ResponseEntity<AccountResponse> findByNickname(@PathVariable
                                                          @NotBlank(message = "Nickname cannot be empty.")
                                                          String nickname) {
        return ResponseEntity.ok(accountService.findByNickname(nickname));
    }

    @PostMapping(value = AccountV1Endpoints.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.create(request));
    }

    @PutMapping(value = AccountV1Endpoints.UPDATE)
    public ResponseEntity<AccountResponse> update(@Valid @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(accountService.update(request));
    }

    @PostMapping(
            value = AccountV1Endpoints.NICKNAME_UPDATE_AVATAR,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AccountResponse> updateAvatar(
            @PathVariable
            @NotBlank(message = "Nickname cannot be empty.")
            String nickname,
            @RequestPart MultipartFile file
    ) {
        return ResponseEntity.ok(accountService.updateAvatar(nickname, file));
    }

    @GetMapping(value = AccountV1Endpoints.CONFIRM_CODE)
    public ResponseEntity<ReportResponse> confirm(@PathVariable
                                                  @NotEmpty(message = "Confirm code cannot be empty.")
                                                  String code) {
        return ResponseEntity.ok(accountService.confirm(code));
    }

    @PostMapping(value = AccountV1Endpoints.UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }
}
