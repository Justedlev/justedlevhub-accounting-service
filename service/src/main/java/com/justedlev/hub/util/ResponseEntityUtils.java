package com.justedlev.hub.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.net.URI;

@UtilityClass
public class ResponseEntityUtils {
    @NonNull
    public static ResponseEntity.BodyBuilder found(@NonNull URI uri) {
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, uri.toString());
    }
}
