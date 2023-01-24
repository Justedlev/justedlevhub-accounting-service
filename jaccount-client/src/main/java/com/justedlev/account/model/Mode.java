package com.justedlev.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.account.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mode implements Serializable {
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ModeType modeType = ModeType.OFFLINE;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp modeAt = Timestamp.valueOf(LocalDateTime.now());
}
