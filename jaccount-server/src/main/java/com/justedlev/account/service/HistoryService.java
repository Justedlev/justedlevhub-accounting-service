package com.justedlev.account.service;

import com.justedlev.account.model.request.HistoryRequest;
import com.justedlev.account.model.response.AccountHistoryResponse;

import java.util.List;

public interface HistoryService {
    List<AccountHistoryResponse> getAccounts(HistoryRequest request);
}
