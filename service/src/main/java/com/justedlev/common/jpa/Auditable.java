package com.justedlev.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;

@Setter(onParam = @__(@Nullable))
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<PK extends Serializable> extends AbstractPersistable<PK> {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @NonNull
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @NonNull
    public Optional<Timestamp> getCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    @NonNull
    public Optional<String> getModifiedBy() {
        return Optional.ofNullable(modifiedBy);
    }

    @NonNull
    public Optional<Timestamp> getModifiedAt() {
        return Optional.ofNullable(modifiedAt);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
