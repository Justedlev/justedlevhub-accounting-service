package com.justedlev.hub.controller;

import com.justedlev.hub.model.response.AccountAuditResponse;
import com.justedlev.hub.service.AccountHistoryService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public ResponseEntity<Page<AccountAuditResponse>>
    findPage(@PathVariable UUID id, @PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(accountHistoryService.getPageById(id, pageable));
    }
}
