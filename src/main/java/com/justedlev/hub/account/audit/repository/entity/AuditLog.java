package com.justedlev.hub.account.audit.repository.entity;

import com.justedlev.hub.account.audit.repository.entity.base.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "audit_log")
public class AuditLog extends Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
    @Column(name = "entity_id", unique = true)
    private String entityId;
    @Column(name = "entity_type", unique = true)
    private String entityType;
    @ToString.Exclude
    @OneToMany(mappedBy = "auditLog")
    @Cascade({
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private List<AuditSnapshot> snapshots;
}
