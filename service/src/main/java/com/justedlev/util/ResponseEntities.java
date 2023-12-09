package com.justedlev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@UtilityClass
public class ResponseEntities {
    @NonNull
    public static <T> ResponseEntity<T> found(@NonNull URI uri) {
        return BodyBuilders.found(uri).build();
    }

    @UtilityClass
    public static class BodyBuilders {
        @NonNull
        public static ResponseEntity.BodyBuilder found(@NonNull URI uri) {
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, uri.toString());
        }
    }
}
