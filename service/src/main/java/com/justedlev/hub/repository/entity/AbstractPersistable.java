package com.justedlev.hub.repository.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@MappedSuperclass
public abstract class AbstractPersistable<PK extends Serializable> implements Persistable<PK> {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private PK id;

    @Transient
    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }

    @Override
    public final boolean equals(Object o) {

        if (Objects.isNull(o)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!Objects.equals(Hibernate.getClass(this), Hibernate.getClass(o))) {
            return false;
        }

        AbstractPersistable<?> that = (AbstractPersistable<?>) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(id);
    }
}
