package com.justedlev.account.controller;

import com.justedlev.account.client.EndpointConstant;
import com.justedlev.account.model.request.HistoryRequest;
import com.justedlev.account.model.response.AccountHistoryResponse;
import com.justedlev.account.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointConstant.V1_HISTORY)
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping(value = EndpointConstant.ACCOUNT)
    public ResponseEntity<List<AccountHistoryResponse>> getAccounts(@Valid @RequestBody HistoryRequest request) {
        var response = historyService.getAccounts(request);

        return ResponseEntity.ok(response);
    }
}
