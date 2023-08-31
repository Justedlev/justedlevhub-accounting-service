package com.justedlev.hub.service.impl;

import com.justedlev.hub.model.request.CreateStatusRequest;
import com.justedlev.hub.model.response.StatusResponse;
import com.justedlev.hub.repository.StatusRepository;
import com.justedlev.hub.repository.entity.Status;
import com.justedlev.hub.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final ModelMapper mapper;

    @Override
    public List<StatusResponse> getAll() {
        return statusRepository.findAll()
                .stream()
                .map(mode -> mapper.map(mode, StatusResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public StatusResponse getById(Long id) {
        var status = statusRepository.getById(id);

        return mapper.map(status, StatusResponse.class);
    }

    @Override
    public StatusResponse create(CreateStatusRequest request) {
        var status = mapper.map(request, Status.class);
        var saved = statusRepository.save(status);

        return mapper.map(saved, StatusResponse.class);
    }

    @Override
    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
