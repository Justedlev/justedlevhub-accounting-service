package com.justedlevhub.account.common.mapper;

import com.justedlevhub.api.model.response.ReportResponse;

public interface ReportMapper {
    ReportResponse toReport(String message, String details);

    ReportResponse toReport(String message);

    ReportResponse toReport();
}
