package com.justedlev.account.component;

import com.justedlev.account.model.request.HistoryRequest;
import com.justedlev.account.model.response.AccountHistoryResponse;

import java.util.List;

public interface HistoryComponent {
    List<AccountHistoryResponse> getAccounts(HistoryRequest request);
}
