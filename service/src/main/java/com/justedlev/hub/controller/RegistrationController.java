package com.justedlev.hub.controller;

import com.justedlev.common.constant.ControllerResources;
import com.justedlev.hub.model.request.RegistrationRequest;
import com.justedlev.hub.service.RegistrationService;
import com.justedlev.util.ServiceHttpHeaders;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.justedlev.common.constant.ControllerResources.Account.ACCOUNTS;

@RestController
@RequestMapping("/v1/registration")
@RequiredArgsConstructor
@Validated
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid RegistrationRequest request) {
        var res = registrationService.registration(request);

        return ResponseEntity.created(URI.create(ControllerResources.API + ACCOUNTS + "/" + res.getUserId()))
                .header(ServiceHttpHeaders.X_USER_ID, String.valueOf(res.getUserId()))
                .build();
    }
}
