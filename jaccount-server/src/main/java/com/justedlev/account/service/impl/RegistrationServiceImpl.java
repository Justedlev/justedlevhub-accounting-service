package com.justedlev.account.service.impl;

import com.justedlev.account.component.RegistrationComponent;
import com.justedlev.account.model.request.RegistrationRequest;
import com.justedlev.account.service.RegistrationService;
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
