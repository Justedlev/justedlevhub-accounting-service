package com.justedlev.account.component;

import com.justedlev.account.model.request.RegistrationRequest;

public interface RegistrationComponent {
    ReportResponse registration(RegistrationRequest registrationRequest);
}