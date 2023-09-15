package com.justedlev.hub.repository.entity;

import com.justedlev.hub.type.AccountMode;
import com.justedlev.hub.type.AccountStatus;
import com.justedlev.hub.type.Gender;
import com.justedlev.hub.util.Generator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.justedlev.hub.type.AccountMode.OFFLINE;
import static com.justedlev.hub.type.AccountStatus.UNCONFIRMED;

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
@Table(name = "accounts")
@NamedEntityGraph(
        name = "eg-account",
        attributeNodes = {
                @NamedAttributeNode("contacts"),
        }
)
public class Account extends Versionable implements Serializable {
    @Serial
    private static final long serialVersionUID = 167144934L;

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false)
    private UUID id;

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

    @Column(name = "avatar", columnDefinition = "text")
    private String avatar;

    @Builder.Default
    @Column(
            name = "confirm_code",
            length = 32,
            nullable = false,
            unique = true,
            updatable = false
    )
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
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE
    })
    private Set<Contact> contacts;

    public AccountMode getAccountMode() {
        return AccountMode.labelOf(mode);
    }

    public AccountStatus getAccountStatus() {
        return AccountStatus.labelOf(status);
    }

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
