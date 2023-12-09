package com.justedlev.hub.service;

import com.justedlev.hub.model.request.RegistrationRequest;
import com.justedlev.hub.model.response.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse registration(RegistrationRequest request);
}
