package com.justedlev.hub.controller;

import com.justedlev.hub.configuration.properties.AccountControllerProperties;
import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.service.AccountService;
import com.justedlev.util.ResponseEntities;
import com.justedlev.util.ServiceHttpHeaders;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Accounts", description = "Endpoints for accounts operations")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Validated
public class AccountController {
    public static final String ACCOUNTS = "/v1/accounts";
    public static final String UPDATE_MODE = "/update-mode";
    public static final String ID_PATH_VAR = "{id}";
    public static final String ID = "/" + ID_PATH_VAR;
    public static final String CODE_PATH_VAR = "{code}";
    public static final String CONFIRM = "/confirm";
    public static final String CONFIRM_CODE = CONFIRM + "/" + CODE_PATH_VAR;

    private final AccountService accountService;
    private final AccountControllerProperties properties;

    @Operation(summary = "Find the page of accounts")
    @ApiResponse(responseCode = "200", description = "Found the page of accounts")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AccountResponse>>
    findPage(@ParameterObject AccountFilterParams params,
             @ParameterObject @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountService.findPageByFilter(params, pageable));
    }

    @Operation(summary = "Find the account by its id")
    @ApiResponse(responseCode = "200")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> getById(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @Operation(summary = "Create new account")
    @ApiResponse(responseCode = "201")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        var account = accountService.create(request);

        return ResponseEntity.created(buildAccountUri(account)).body(account);
    }

    @Operation(summary = "Update account")
    @ApiResponse(responseCode = "200")
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> update(@PathVariable @NotNull UUID id,
                                                  @Valid @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(accountService.updateById(id, request));
    }

//    @Operation(summary = "Update account avatar")
//    @ApiResponse(responseCode = "200")
//    @PatchMapping(
//            value = ID_AVATAR,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<AccountResponse> updateAvatar(@PathVariable @NotNull UUID id,
//                                                        @RequestPart MultipartFile file) {
//        return ResponseEntity.ok(accountService.updateAvatar(id, file));
//    }

    @Operation(summary = "Account confirmation")
    @ApiResponse(responseCode = "302")
    @PatchMapping(value = "/confirm/{code}")
    public ResponseEntity<Void> confirmCode(@PathVariable @NotEmpty String code) {
        var id = accountService.confirm(code);
        return ResponseEntities.found(buildAccountUri(id));
    }

    @Operation(summary = "Update account mode")
    @ApiResponse(responseCode = "200")
    @PatchMapping(value = UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }

    @Operation(summary = "Delete account by its id")
    @ApiResponse(description = "Successfully deleted")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(value = ID)
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        accountService.deleteById(id);
        return ResponseEntity
                .noContent()
                .header(ServiceHttpHeaders.X_ALERT, "account.deleted")
                .build();
    }

    private URI buildAccountUri(AccountResponse response) {
        return buildAccountUri(response.getId());
    }

    private URI buildAccountUri(UUID id) {
        return UriComponentsBuilder.fromUriString(properties.getPath()).path("/" + id).build().toUri();
    }
}
