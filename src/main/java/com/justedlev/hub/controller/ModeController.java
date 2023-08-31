package com.justedlev.hub.controller;

import com.justedlev.hub.configuration.properties.Properties;
import com.justedlev.hub.model.request.CreateModeRequest;
import com.justedlev.hub.model.response.ModeResponse;
import com.justedlev.hub.service.ModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/modes")
@RequiredArgsConstructor
@Validated
public class ModeController {
    private final ModeService modeService;
    private final Properties.Service service;

    @GetMapping
    public ResponseEntity<List<ModeResponse>> getAll() {
        return ResponseEntity.ok(modeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(modeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ModeResponse> create(@RequestBody CreateModeRequest request) {
        var mode = modeService.create(request);

        return ResponseEntity.created(URI.create(service.getUrl() + "/modes/" + mode.getId()))
                .body(mode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
