package com.justedlev.hub.controller;

import com.justedlev.hub.configuration.properties.Properties;
import com.justedlev.hub.constant.ControllerResources;
import com.justedlev.hub.model.params.AccountFilterParams;
import com.justedlev.hub.model.request.CreateAccountRequest;
import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.request.UpdateAccountRequest;
import com.justedlev.hub.model.response.AccountResponse;
import com.justedlev.hub.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(ControllerResources.ACCOUNTS)
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;
    private final Properties.Service serviceProperties;

    @GetMapping(value = ControllerResources.PAGE)
    public ResponseEntity<Page<AccountResponse>>
    findPage(AccountFilterParams params, @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountService.findPageByFilter(params, pageable));
    }

    @GetMapping(value = ControllerResources.NICKNAME)
    public ResponseEntity<AccountResponse>
    findByNickname(@PathVariable @NotBlank @NotNull @NotEmpty String nickname) {
        return ResponseEntity.ok(accountService.findByNickname(nickname));
    }

    @PostMapping(value = ControllerResources.CREATE)
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        var account = accountService.create(request);
        var createdUri = UriComponentsBuilder.fromUriString(serviceProperties.getUrl())
                .path(ControllerResources.ACCOUNTS)
                .path("/" + account.getNickname())
                .build()
                .toUri();

        return ResponseEntity
                .created(createdUri)
                .body(account);
    }

    @PutMapping(value = ControllerResources.NICKNAME)
    public ResponseEntity<AccountResponse> update(@PathVariable @NotBlank @NotNull @NotEmpty String nickname,
                                                  @Valid @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(accountService.updateByNickname(nickname, request));
    }

    @PatchMapping(
            value = ControllerResources.NICKNAME_UPDATE_AVATAR,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AccountResponse> updateAvatar(
            @PathVariable
            @NotBlank(message = "Nickname cannot be empty.")
            String nickname,
            @RequestPart MultipartFile file) {
        return ResponseEntity.ok(accountService.updateAvatar(nickname, file));
    }

    @GetMapping(value = ControllerResources.CONFIRM_CODE)
    public ResponseEntity<String> confirm(@PathVariable @NotEmpty String code) {
        var nickname = accountService.confirm(code);
        return new ResponseEntity<>("redirect:" + serviceProperties.getUrl() + "/" + nickname, HttpStatus.FOUND);
    }

    @PutMapping(value = ControllerResources.UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }

    @DeleteMapping(value = ControllerResources.NICKNAME)
    public ResponseEntity<Void> delete(@PathVariable @NotBlank @NotNull @NotEmpty String nickname) {
        accountService.deleteByNickname(nickname);
        return ResponseEntity
                .noContent()
                .header("X-alert","account.deleted")
                .build();
    }
}
