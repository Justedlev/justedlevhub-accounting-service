package com.justedlev.account.repository;

import com.justedlev.account.repository.entity.JAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JAuditRepository extends JpaRepository<JAudit, Long> {
}