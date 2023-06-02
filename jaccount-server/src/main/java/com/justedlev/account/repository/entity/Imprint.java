package com.justedlev.account.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Imprint implements Serializable {
    private String fieldName;
    private String fieldType;
    private String oldValue;
    private String newValue;
}
