package com.justedlev.hub.service;

import com.justedlev.hub.model.request.CreateModeRequest;
import com.justedlev.hub.model.response.ModeResponse;

import java.util.List;

public interface ModeService {
    List<ModeResponse> getAll();

    ModeResponse getById(Long id);

    ModeResponse create(CreateModeRequest request);

    void delete(Long id);
}
