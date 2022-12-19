package com.justedlev.account.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.account.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountModeRequest {
    private Collection<ModeType> fromModes;
    @NotNull(message = "Made cannot be empty.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ModeType toMode;
}
