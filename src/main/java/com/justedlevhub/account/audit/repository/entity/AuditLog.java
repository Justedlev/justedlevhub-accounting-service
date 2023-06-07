package com.justedlevhub.account.audit.repository.entity;

import com.justedlevhub.account.audit.repository.entity.base.Auditable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "audit_log")
public class AuditLog extends Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
    @Column(name = "entity_id")
    private String entityId;
    @Column(name = "entity_type")
    private String entityType;
    @Singular
    @Type(type = "jsonb")
    @Column(name = "imprints", columnDefinition = "jsonb")
    private List<AuditImprint> imprints;
}
