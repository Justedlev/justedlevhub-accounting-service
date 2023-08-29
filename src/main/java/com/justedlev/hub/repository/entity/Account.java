package com.justedlev.hub.repository.entity;

import com.justedlev.hub.api.type.AccountStatus;
import com.justedlev.hub.api.type.Gender;
import com.justedlev.hub.api.type.ModeType;
import com.justedlev.hub.util.DateTimeUtils;
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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "account")
@Audited
public class Account extends Auditable implements Serializable {
    @Id
    @UuidGenerator
    @Column(name = "account_id", updatable = false)
    private UUID id;
    @Column(name = "nick_name", nullable = false)
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
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private AccountStatus status = AccountStatus.UNCONFIRMED;
    @Getter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private ModeType mode = ModeType.OFFLINE;
    @Getter
    @Builder.Default
    @Column(name = "mode_at", nullable = false)
    private Timestamp modeAt = DateTimeUtils.nowTimestamp();
    @Version
    @Column(name = "version")
    private Long version;
    @Singular
    @ToString.Exclude
    @NotAudited
    @OneToMany
    @JoinTable(
            name = "account_contact",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    @Cascade({
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Set<Contact> contacts;

    public void setMode(ModeType mode) {
        this.mode = mode;
        this.setModeAt(DateTimeUtils.nowTimestamp());
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
        return getClass().hashCode();
    }
}
