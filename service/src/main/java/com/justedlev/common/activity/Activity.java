package com.justedlev.common.activity;

import com.justedlev.hub.repository.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.UUID;

@DynamicUpdate
@Entity
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Activity extends Auditable<UUID> implements Serializable {
    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "entity_type")
    private Class<?> entityType;

    @Column(name = "value_type")
    private Class<?> valueType;

    @Column(name = "current_value")
    private String value;

    @Column(name = "previous_value")
    private String previousValue;
}
