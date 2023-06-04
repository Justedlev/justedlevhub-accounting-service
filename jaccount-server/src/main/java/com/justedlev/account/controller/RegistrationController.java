package com.justedlev.account.controller;

import com.justedlev.account.model.request.RegistrationRequest;
import com.justedlev.account.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/registration")
@RequiredArgsConstructor
@Validated
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationRequest request) {
        registrationService.registration(request);

        return ResponseEntity.ok().build();
    }
}
