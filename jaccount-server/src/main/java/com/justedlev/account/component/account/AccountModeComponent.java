package com.justedlev.account.component.account;

import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;

import java.util.List;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(UpdateAccountModeRequest command);
}
