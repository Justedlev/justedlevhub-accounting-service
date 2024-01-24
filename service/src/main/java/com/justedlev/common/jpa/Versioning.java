package com.justedlev.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Versioning<PK extends Serializable> extends Auditable<PK> {
    @Setter(onParam = @__({@Nullable}))
    @Version
    @Column(name = "version")
    private Long version;

    public @NonNull Optional<Long> getVersion() {
        return Optional.ofNullable(version);
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
