package com.justedlev.hub.component.account;

import com.justedlev.hub.model.request.UpdateAccountModeRequest;
import com.justedlev.hub.model.response.AccountResponse;

import java.util.List;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(UpdateAccountModeRequest command);
}
