package com.justedlevhub.account.audit;

import com.justedlevhub.account.audit.repository.entity.AuditLog;
import com.justedlevhub.account.audit.repository.entity.base.Auditable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AuditLogger {
    Optional<AuditLog> audit(Auditable auditable);

    List<AuditLog> auditAll(Collection<Auditable> auditableCollection);
}
