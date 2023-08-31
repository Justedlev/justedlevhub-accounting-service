package com.justedlev.hub.service.impl;

import com.justedlev.hub.model.request.CreateModeRequest;
import com.justedlev.hub.model.response.ModeResponse;
import com.justedlev.hub.repository.ModeRepository;
import com.justedlev.hub.repository.entity.Mode;
import com.justedlev.hub.service.ModeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModeServiceImpl implements ModeService {
    private final ModeRepository modeRepository;
    private final ModelMapper mapper;

    @Override
    public List<ModeResponse> getAll() {
        return modeRepository.findAll()
                .stream()
                .map(mode -> mapper.map(mode, ModeResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ModeResponse getById(Long id) {
        var mode = modeRepository.getById(id);

        return mapper.map(mode, ModeResponse.class);
    }

    @Override
    public ModeResponse create(CreateModeRequest request) {
        var mode = mapper.map(request, Mode.class);
        var saved = modeRepository.save(mode);

        return mapper.map(saved, ModeResponse.class);
    }

    @Override
    public void delete(Long id) {
        modeRepository.deleteById(id);
    }
}
