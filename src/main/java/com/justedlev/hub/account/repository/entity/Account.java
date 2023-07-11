package com.justedlev.hub.account.repository.entity;

import com.justedlev.hub.account.audit.annotation.AuditValue;
import com.justedlev.hub.account.audit.repository.entity.base.Auditable;
import com.justedlev.hub.account.util.DateTimeUtils;
import com.justedlev.hub.account.util.Generator;
import com.justedlev.hub.api.type.AccountStatus;
import com.justedlev.hub.api.type.Gender;
import com.justedlev.hub.api.type.ModeType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "account")
public class Account extends Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "account_id", updatable = false)
    private UUID id;
    @AuditValue
    @Column(name = "nick_name", nullable = false)
    private String nickname;
    @AuditValue
    @Column(name = "first_name")
    private String firstName;
    @AuditValue
    @Column(name = "last_name")
    private String lastName;
    @AuditValue
    @Column(name = "birth_date")
    private Timestamp birthDate;
    @AuditValue
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Type(type = "jsonb")
    @Column(name = "avatar", columnDefinition = "jsonb")
    private Avatar avatar;
    @AuditValue(hide = true)
    @Builder.Default
    @Column(
            name = "activation_code",
            length = 32,
            nullable = false,
            unique = true,
            updatable = false
    )
    private String activationCode = Generator.generateActivationCode();
    @AuditValue
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
