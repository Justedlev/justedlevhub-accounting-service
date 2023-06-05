package com.justedlev.account.common.mapper;

import com.justedlev.common.model.response.ReportResponse;

public interface ReportMapper {
    ReportResponse toReport(String message, String details);

    ReportResponse toReport(String message);

    ReportResponse toReport();
}
