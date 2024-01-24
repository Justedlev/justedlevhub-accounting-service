package com.justedlev.hub.repository.entity;

import com.justedlev.common.jpa.Auditable;
import com.justedlev.common.jpa.Versioning;
import com.justedlev.hub.type.Gender;
import com.justedlev.util.Generator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.data.history.Revision;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.justedlev.hub.type.AccountMode.OFFLINE;
import static com.justedlev.hub.type.AccountStatus.UNCONFIRMED;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Audited
@AuditOverride(forClass = Auditable.class)
@Entity
@DynamicUpdate
@Table(name = "accounts")
@NamedEntityGraph(
        name = Account.ENTITY_GRAPH_NAME,
        attributeNodes = {
                @NamedAttributeNode("contacts"),
        }
)
public class Account extends Versioning<UUID> implements Serializable {
    public static final String ENTITY_GRAPH_NAME = "account-entity-graph";

    @NotBlank
    @NotEmpty
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private Timestamp birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Lob
    @Column(name = "avatar", length = 1000)
    private String avatar;

    @Builder.Default
    @Column(name = "confirm_code", length = 32, nullable = false, unique = true, updatable = false)
    private String confirmCode = Generator.generateConfirmCode();

    @NotEmpty
    @NotBlank
    @Builder.Default
    @Column(name = "status", nullable = false)
    private String status = UNCONFIRMED.getLabel();

    @NotEmpty
    @NotBlank
    @Builder.Default
    @Column(name = "mode", nullable = false)
    private String mode = OFFLINE.getLabel();

    @Singular
    @ToString.Exclude
    @OneToMany(mappedBy = "account")
    @Cascade({
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.DELETE_ORPHAN,
    })
    private Set<Contact> contacts;

    private transient List<Revision<Long, Account>> revisions;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
