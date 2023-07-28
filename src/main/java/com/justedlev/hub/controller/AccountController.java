package com.justedlev.hub.controller;

import com.justedlev.hub.api.model.params.AccountFilterParams;
import com.justedlev.hub.api.model.request.CreateAccountRequest;
import com.justedlev.hub.api.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.api.model.request.UpdateAccountRequest;
import com.justedlev.hub.api.model.response.AccountResponse;
import com.justedlev.hub.constant.AccountV1Endpoints;
import com.justedlev.hub.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(AccountV1Endpoints.V1_ACCOUNT)
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;

    @GetMapping(value = "/find-by-filter")
    public ResponseEntity<Page<AccountResponse>>
    findPage(AccountFilterParams params, @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountService.findPageByFilter(params, pageable));
    }

    @GetMapping(value = AccountV1Endpoints.NICKNAME)
    public ResponseEntity<AccountResponse> findByNickname(@PathVariable @NotBlank String nickname) {
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

    @PatchMapping(
            value = AccountV1Endpoints.NICKNAME_UPDATE_AVATAR,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AccountResponse> updateAvatar(
            @PathVariable
            @NotBlank(message = "Nickname cannot be empty.")
            String nickname,
            @RequestPart MultipartFile file) {
        return ResponseEntity.ok(accountService.updateAvatar(nickname, file));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = AccountV1Endpoints.CONFIRM_CODE)
    public String confirm(
            @PathVariable
            @NotEmpty(message = "Confirm code cannot be empty.")
            String code) {
        return accountService.confirm(code);
    }

    @PutMapping(value = AccountV1Endpoints.UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }
}
