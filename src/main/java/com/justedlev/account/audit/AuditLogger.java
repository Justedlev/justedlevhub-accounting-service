package com.justedlev.account.audit;

import com.justedlev.account.audit.repository.entity.AuditLog;
import com.justedlev.common.entity.Auditable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AuditLogger {
    Optional<AuditLog> audit(Auditable auditable);

    List<AuditLog> auditAll(Collection<Auditable> auditableCollection);
}
