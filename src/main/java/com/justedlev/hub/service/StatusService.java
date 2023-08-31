package com.justedlev.hub.service;

import com.justedlev.hub.model.request.CreateStatusRequest;
import com.justedlev.hub.model.response.StatusResponse;

import java.util.List;

public interface StatusService {
    List<StatusResponse> getAll();

    StatusResponse getById(Long id);

    StatusResponse create(CreateStatusRequest request);

    void delete(Long id);
}
