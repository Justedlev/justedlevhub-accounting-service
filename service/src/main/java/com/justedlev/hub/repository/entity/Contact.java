package com.justedlev.hub.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Audited
@AuditOverride(forClass = Auditable.class)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "contacts")
@NamedEntityGraph(
        name = "contact-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("account"),
        }
)
public class Contact extends Versionable implements Serializable {
    @Serial
    private static final long serialVersionUID = 6790844800L;

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @Column(name = "value")
    private String value;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_contact",
            joinColumns = @JoinColumn(
                    name = "contact_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "account_id",
                    referencedColumnName = "id"
            )
    )
    @Cascade({CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE,})
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contact contact = (Contact) o;
        return getId() != null && Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
