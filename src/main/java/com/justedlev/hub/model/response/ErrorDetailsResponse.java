package com.justedlev.hub.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorDetailsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Builder.Default
    private Date timestamp = new Date();
    private String message;
    private String details;
}
