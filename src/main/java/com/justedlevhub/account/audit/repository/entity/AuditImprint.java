package com.justedlevhub.account.audit.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditImprint implements Serializable {
    private boolean hide;
    private String fieldName;
    private Class<?> fieldType;
    private String oldValue;
    private String newValue;
}