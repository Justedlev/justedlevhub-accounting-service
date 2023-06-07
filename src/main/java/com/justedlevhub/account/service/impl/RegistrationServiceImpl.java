package com.justedlevhub.account.service.impl;

import com.justedlevhub.account.component.RegistrationComponent;
import com.justedlevhub.account.service.RegistrationService;
import com.justedlevhub.api.model.request.RegistrationRequest;
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
