package com.justedlev.account.controller;

import com.justedlev.account.client.EndpointConstant;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;
import com.justedlev.account.service.AccountService;
import com.justedlev.model.request.PaginationRequest;
import com.justedlev.model.response.PageResponse;
import com.justedlev.model.response.ReportResponse;
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
@RequestMapping(EndpointConstant.V1_ACCOUNT)
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;

    @PostMapping(value = EndpointConstant.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.create(request));
    }

    @PostMapping(value = EndpointConstant.PAGE)
    public ResponseEntity<PageResponse<List<AccountResponse>>> getPage(@Valid @RequestBody PaginationRequest request) {
        return ResponseEntity.ok(accountService.getPage(request));
    }

    @GetMapping(value = EndpointConstant.NICKNAME)
    public ResponseEntity<AccountResponse> getAccountByNickname(@PathVariable
                                                                @NotBlank(message = "Nickname cannot be empty.")
                                                                String nickname) {
        return ResponseEntity.ok(accountService.getByNickname(nickname));
    }

    @PutMapping(value = EndpointConstant.NICKNAME_UPDATE)
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable
                                                         @NotBlank(message = "Nickname cannot be empty.")
                                                         String nickname,
                                                         @Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.update(nickname, request));
    }

    @PostMapping(value = EndpointConstant.NICKNAME_UPDATE_AVATAR, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AccountResponse> updateAccountAvatar(@PathVariable
                                                               @NotBlank(message = "Nickname cannot be empty.")
                                                               String nickname,
                                                               @RequestPart MultipartFile file) {
        return ResponseEntity.ok(accountService.updateAvatar(nickname, file));
    }

    @GetMapping(value = EndpointConstant.CONFIRM_CODE)
    public ResponseEntity<ReportResponse> confirm(@PathVariable
                                                  @NotEmpty(message = "Confirm code cannot be empty.")
                                                  String code) {
        return ResponseEntity.ok(accountService.confirm(code));
    }

    @PostMapping(value = EndpointConstant.UPDATE_MODE)
    public ResponseEntity<List<AccountResponse>> updateMode(@Valid @RequestBody UpdateAccountModeRequest request) {
        return ResponseEntity.ok(accountService.updateMode(request));
    }
}
