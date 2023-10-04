package com.justedlev.hub.controller;

import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.service.AccountService;
import com.justedlev.hub.util.ResponseEntityUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.justedlev.hub.constant.ControllerResources.API;
import static com.justedlev.hub.constant.ControllerResources.Account.*;

@RestController
@RequestMapping(ACCOUNTS)
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Page<AccountResponse>>
    findPage(AccountFilterParams params, @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountService.findPageByFilter(params, pageable));
    }

    @GetMapping(value = ID)
    public ResponseEntity<AccountResponse> getById(@PathVariable @NotNull UUID id) {
        var response = accountService.getById(id);

        return ResponseEntity.ok()
                .header("X-user-id", response.getId().toString())
                .body(response);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        var account = accountService.create(request);
        return ResponseEntity.created(buildAccountUri(account)).body(account);
    }

    @PatchMapping(value = ID)
    public ResponseEntity<AccountResponse> update(@PathVariable @NotNull UUID id,
                                                  @Valid @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(accountService.updateById(id, request));
    }

    @PatchMapping(value = ID_AVATAR, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AccountResponse> updateAvatar(@PathVariable @NotNull UUID id,
                                                        @RequestPart MultipartFile file) {
        return ResponseEntity.ok(accountService.updateAvatar(id, file));
    }

    @PatchMapping(value = CONFIRM_CODE)
    public ResponseEntity<Void> confirm(@PathVariable @NotEmpty String code) {
        var id = accountService.confirm(code);
        return ResponseEntityUtils.found(buildAccountUri(id)).build();
    }

    @PatchMapping(value = UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        accountService.deleteById(id);
        return ResponseEntity
                .noContent()
                .header("X-alert", "account.deleted")
                .build();
    }

    private URI buildAccountUri(AccountResponse response) {
        return buildAccountUri(response.getId());
    }

    private URI buildAccountUri(UUID id) {
        return UriComponentsBuilder.fromUriString(API).path(ACCOUNTS).path("/" + id).build().toUri();
    }
}
