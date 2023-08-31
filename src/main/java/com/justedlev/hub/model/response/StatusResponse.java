package com.justedlev.hub.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatusResponse implements Serializable {
    private Long id;
    private String label;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
