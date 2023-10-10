package com.justedlev.hub.controller;

import com.justedlev.hub.configuration.properties.ApplicationProperties;
import com.justedlev.hub.model.request.RegistrationRequest;
import com.justedlev.hub.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static com.justedlev.hub.constant.ControllerResources.Account.ACCOUNTS;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Validated
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ApplicationProperties.Service service;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid RegistrationRequest request) {
        registrationService.registration(request);
        var createdUri = UriComponentsBuilder.fromUriString(service.getUrl())
                .path(ACCOUNTS)
                .path("/" + request.getNickname())
                .build()
                .toUri();

        return ResponseEntity.created(createdUri).build();
    }
}
