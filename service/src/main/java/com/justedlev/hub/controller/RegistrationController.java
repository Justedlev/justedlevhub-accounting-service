package com.justedlev.hub.controller;

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

@RestController
@RequestMapping("${controller.registration.path}")
@RequiredArgsConstructor
@Validated
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(value = "${controller.registration.resources.sign-up}")
    public ResponseEntity<Void> signUp(@RequestBody @Valid RegistrationRequest request) {
        var res = registrationService.registration(request);

        return ResponseEntity.created(URI.create(AccountController.ACCOUNTS + "/" + res.getUserId()))
                .header(ServiceHttpHeaders.X_USER_ID, String.valueOf(res.getUserId()))
                .build();
    }
}
