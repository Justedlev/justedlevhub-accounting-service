package com.justedlev.hub.controller;

import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.service.AccountService;
import com.justedlev.hub.util.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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

@Tag(name = "Accounts", description = "Endpoints for accounts operations")
@RestController
@RequestMapping(ACCOUNTS)
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Find the page of accounts")
    @ApiResponse(responseCode = "200", description = "Found the page of accounts")
    @GetMapping
    public ResponseEntity<Page<AccountResponse>>
    findPage(@ParameterObject AccountFilterParams params,
             @ParameterObject @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountService.findPageByFilter(params, pageable));
    }

    @Operation(summary = "Find the account by its id")
    @GetMapping(value = ID)
    public ResponseEntity<AccountResponse> getById(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @Operation(summary = "Create new account")
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        var account = accountService.create(request);

        return ResponseEntity.created(buildAccountUri(account)).body(account);
    }

    @Operation(summary = "Update account")
    @PatchMapping(value = ID)
    public ResponseEntity<AccountResponse> update(@PathVariable @NotNull UUID id,
                                                  @Valid @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(accountService.updateById(id, request));
    }

    @Operation(summary = "Update account avatar")
    @PatchMapping(value = ID_AVATAR, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AccountResponse> updateAvatar(@PathVariable @NotNull UUID id,
                                                        @RequestPart MultipartFile file) {
        return ResponseEntity.ok(accountService.updateAvatar(id, file));
    }

    @Operation(summary = "Account confirmation")
    @PatchMapping(value = CONFIRM_CODE)
    public ResponseEntity<Void> confirm(@PathVariable @NotEmpty String code) {
        var id = accountService.confirm(code);
        return ResponseEntityUtils.found(buildAccountUri(id)).build();
    }

    @Operation(summary = "Update account mode")
    @PatchMapping(value = UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }

    @Operation(summary = "Delete account by its id")
    @ApiResponse(responseCode = "204", description = "Successfully deleted")
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
