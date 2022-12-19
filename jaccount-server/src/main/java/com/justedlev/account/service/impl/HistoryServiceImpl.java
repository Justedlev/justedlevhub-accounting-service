package com.justedlev.account.service.impl;

import com.justedlev.account.component.HistoryComponent;
import com.justedlev.account.model.request.HistoryRequest;
import com.justedlev.account.model.response.AccountHistoryResponse;
import com.justedlev.account.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryComponent historyComponent;

    @Override
    public List<AccountHistoryResponse> getAccounts(HistoryRequest request) {
        return historyComponent.getAccounts(request);
    }
}
