package com.justedlev.hub.util;

import com.justedlev.hub.constant.ControllerResources;
import com.justedlev.hub.model.response.AccountResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AccountResponseEntityUtilities {
    public final UriComponentsBuilder uriBuilder;

    public AccountResponseEntityUtilities(UriComponentsBuilder serviceUriBuilder) {
        this.uriBuilder = serviceUriBuilder.path(ControllerResources.Account.ACCOUNTS);
    }

    public ResponseEntity<AccountResponse> created(AccountResponse response) {
        var uri = uriBuilder.path("/" + response.getNickname()).build().toUri();

        return ResponseEntity.created(uri)
                .body(response);
    }

    public ResponseEntity<Void> found(AccountResponse response) {
        var uri = uriBuilder.path("/" + response.getNickname()).toUriString();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, uri)
                .build();
    }
}
