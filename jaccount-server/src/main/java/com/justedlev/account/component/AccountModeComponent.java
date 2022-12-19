package com.justedlev.account.component;

import java.util.List;

import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(UpdateAccountModeRequest command);
}
