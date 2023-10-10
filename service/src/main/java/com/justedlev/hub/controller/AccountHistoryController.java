package com.justedlev.hub.controller;

import com.justedlev.hub.model.response.AccountAuditResponse;
import com.justedlev.hub.service.AccountHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/history/accounts")
@RequiredArgsConstructor
@Validated
public class AccountHistoryController {
    private final AccountHistoryService accountHistoryService;

    @Operation(summary = "Find the page of account history by its id")
    @ApiResponse(
            responseCode = "200",
            description = "Found the page of accounts"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Page<AccountAuditResponse>>
    findPage(@PathVariable UUID id, @ParameterObject @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountHistoryService.getPageById(id, pageable));
    }
}
