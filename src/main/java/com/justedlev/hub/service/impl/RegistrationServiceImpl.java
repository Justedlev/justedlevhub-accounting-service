package com.justedlev.hub.service.impl;

import com.justedlev.hub.api.model.request.RegistrationRequest;
import com.justedlev.hub.component.RegistrationComponent;
import com.justedlev.hub.service.RegistrationService;
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
