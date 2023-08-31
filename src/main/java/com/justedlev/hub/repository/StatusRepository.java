package com.justedlev.hub.repository;

import com.justedlev.hub.repository.entity.Status;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface StatusRepository extends JpaRepository<Status, Long> {
    @NonNull
    @Override
    default Status getById(@NonNull Long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}