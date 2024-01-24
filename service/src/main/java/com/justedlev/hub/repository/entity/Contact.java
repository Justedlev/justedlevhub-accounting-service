package com.justedlev.hub.repository.entity;

import com.justedlev.common.jpa.Auditable;
import com.justedlev.common.jpa.Versioning;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.UUID;

@Audited
@AuditOverride(forClass = Auditable.class)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "contacts")
@NamedEntityGraph(
        name = Contact.ENTITY_GRAPH_NAME,
        attributeNodes = {@NamedAttributeNode("account"),}
)
public class Contact extends Versioning<UUID> implements Serializable {
    public static final String ENTITY_GRAPH_NAME = "contact-entity-graph";

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @Column(name = "value")
    private String value;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_contact",
            joinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id")
    )
    @Cascade({
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.REMOVE,
    })
    private Account account;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
