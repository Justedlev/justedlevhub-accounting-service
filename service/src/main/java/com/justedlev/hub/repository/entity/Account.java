package com.justedlev.hub.repository.entity;

import com.justedlev.hub.type.Gender;
import com.justedlev.hub.util.Generator;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Audited
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "accounts")
@NamedEntityGraph(
        name = "eg-account",
        attributeNodes = {
                @NamedAttributeNode("contacts"),
                @NamedAttributeNode("mode"),
        }
)
public class Account extends Versionable implements Serializable {
    @Serial
    private static final long serialVersionUID = 31196L;

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "nickname", nullable = false)
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

    @Type(JsonBinaryType.class)
    @Column(name = "avatar", columnDefinition = "jsonb")
    private Avatar avatar;

    @Builder.Default
    @Column(
            name = "activation_code",
            length = 32,
            nullable = false,
            unique = true,
            updatable = false
    )
    private String activationCode = Generator.generateActivationCode();

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Cascade({
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Status status;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "mode_id", referencedColumnName = "id")
    @Cascade({
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Mode mode;

    @Singular
    @ToString.Exclude
    @NotAudited
    @OneToMany(mappedBy = "account")
    @Cascade({
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Set<Contact> contacts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
