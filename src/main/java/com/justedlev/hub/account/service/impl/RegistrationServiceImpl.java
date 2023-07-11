package com.justedlev.hub.account.service.impl;

import com.justedlev.hub.account.component.RegistrationComponent;
import com.justedlev.hub.account.service.RegistrationService;
import com.justedlev.hub.api.model.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationComponent registrationComponent;

    @Override
    public void registration(RegistrationRequest request) {
        registrationComponent.registration(request);
    }
}
