package com.justedlev.hub.controller;

import com.justedlev.hub.configuration.properties.Properties;
import com.justedlev.hub.model.request.CreateStatusRequest;
import com.justedlev.hub.model.response.StatusResponse;
import com.justedlev.hub.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/statuses")
@RequiredArgsConstructor
@Validated
public class StatusController {
    private final StatusService statusService;

    private final Properties.Service service;

    @GetMapping
    public ResponseEntity<List<StatusResponse>> getAll() {
        return ResponseEntity.ok(statusService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StatusResponse> create(@RequestBody CreateStatusRequest request) {
        var mode = statusService.create(request);

        return ResponseEntity.created(URI.create(service.getUrl() + "/statuses/" + mode.getId()))
                .body(mode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        statusService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
