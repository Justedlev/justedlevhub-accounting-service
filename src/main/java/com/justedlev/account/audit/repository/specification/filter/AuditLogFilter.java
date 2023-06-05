package com.justedlev.account.audit.repository.specification.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogFilter implements Serializable {
    private Collection<UUID> ids;
    private Collection<String> entityIds;
    private Collection<String> entityTypes;
}
