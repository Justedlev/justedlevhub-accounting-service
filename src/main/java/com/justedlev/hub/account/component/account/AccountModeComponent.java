package com.justedlev.hub.account.component.account;

import com.justedlev.hub.api.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.api.model.response.AccountResponse;

import java.util.List;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(UpdateAccountModeRequest command);
}
