package com.justedlev.common.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ActivityRegistrarRepository<R extends Serializable> extends Repository<Activity, Long> {
    Optional<Activity> findLastById(R id);

    List<Activity> findAllById(R id);

    Page<Activity> findAllById(R id, Pageable pageable);
}
