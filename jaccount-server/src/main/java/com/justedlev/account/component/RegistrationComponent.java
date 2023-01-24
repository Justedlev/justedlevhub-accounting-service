package com.justedlev.account.component;

import com.justedlev.account.model.request.RegistrationRequest;
import com.justedlev.common.model.response.ReportResponse;

public interface RegistrationComponent {
    ReportResponse registration(RegistrationRequest registrationRequest);
}