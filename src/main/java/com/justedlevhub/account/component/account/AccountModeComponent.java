package com.justedlevhub.account.component.account;

import com.justedlevhub.api.model.request.UpdateAccountModeRequest;
import com.justedlevhub.api.model.response.AccountResponse;

import java.util.List;

public interface AccountModeComponent {
    List<AccountResponse> updateMode(UpdateAccountModeRequest command);
}
