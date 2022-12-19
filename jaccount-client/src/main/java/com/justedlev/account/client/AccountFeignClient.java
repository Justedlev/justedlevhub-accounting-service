package com.justedlev.account.client;

import com.justedlev.account.client.configuration.AccountFeignClientConfiguration;
import com.justedlev.account.model.request.AccountRequest;
import com.justedlev.account.model.request.UpdateAccountModeRequest;
import com.justedlev.account.model.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "accounting-api-client",
        url = "${justedlev-service.accounting.client.url}",
        configuration = AccountFeignClientConfiguration.class
)
public interface AccountFeignClient {
    @PostMapping(value = "/v1/account/update-mode")
    List<AccountResponse> updateMode(@RequestBody UpdateAccountModeRequest request);

    @PostMapping(value="/v1/account/create")
    AccountResponse create(@RequestBody AccountRequest request);
}
