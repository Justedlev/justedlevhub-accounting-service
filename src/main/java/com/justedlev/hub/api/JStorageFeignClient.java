package com.justedlev.hub.api;

import com.justedlev.hub.configuration.JStorageFeignClientConfiguration;
import com.justedlev.hub.api.model.response.AttachmentInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "jstorage-api-client",
        url = "${jstorage.client.url}",
        configuration = JStorageFeignClientConfiguration.class
)
public interface JStorageFeignClient {
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<AttachmentInfoResponse> upload(@RequestPart List<MultipartFile> files);

    @DeleteMapping(value = "/file/delete/{id}")
    AttachmentInfoResponse delete(@PathVariable UUID id);

    @GetMapping(value = "/file/download/{id}/{filename}")
    Resource download(@PathVariable UUID id, @PathVariable String filename);
}
