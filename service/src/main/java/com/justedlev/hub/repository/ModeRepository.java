package com.justedlev.hub.repository;

import com.justedlev.hub.repository.entity.Mode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ModeRepository extends JpaRepository<Mode, Long> {
    @NonNull
    @Override
    default Mode getById(@NonNull Long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}